package com.example.mazaady.presentation.listScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazaady.domain.model.Launch
import com.example.mazaady.domain.useCase.GetFavoriteLaunchesUseCase
import com.example.mazaady.domain.useCase.GetLaunchesUseCase
import com.example.mazaady.domain.useCase.IsFavoriteUseCase
import com.example.mazaady.domain.useCase.LogoutUseCase
import com.example.mazaady.domain.useCase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesListViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getFavoriteLaunchesUseCase: GetFavoriteLaunchesUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LaunchesListState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LaunchesListEffect>()
    val effect = _effect.asSharedFlow()

    init {
        handleIntent(LaunchesListIntent.LoadLaunches)
        observeFavorites()
        viewModelScope.launch {
            state.collect { state ->
                Log.d(
                    "LaunchesListViewModel",
                    "State updated - Launches: ${state.launches.size}, Favorites: ${state.favoriteLaunchIds.size}"
                )
                Log.d("LaunchesListViewModel", "Favorite IDs: ${state.favoriteLaunchIds}")
            }
        }
    }

    fun handleIntent(intent: LaunchesListIntent) {
        when (intent) {
            is LaunchesListIntent.LoadLaunches -> loadLaunches()
            is LaunchesListIntent.RetryLoad -> loadLaunches()
            is LaunchesListIntent.OnLaunchClick -> navigateToDetail(intent.launchId)
            is LaunchesListIntent.OnFavoriteClick -> toggleFavorite(intent.launch)
        }
    }

    private fun loadLaunches() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = getLaunchesUseCase()

            result.fold(
                onSuccess = { launches ->
                    _state.update {
                        it.copy(
                            launches = launches,
                            isLoading = false,
                            error = null
                        )
                    }
                    // Favorites are already being observed, no need to check individually
                },
                onFailure = { error ->
                    val errorMessage = error.message ?: "Unknown error occurred"
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = errorMessage
                        )
                    }
                    _effect.emit(LaunchesListEffect.ShowError(errorMessage))
                }
            )
        }
    }

    /**
     * FIX: Observe all favorites continuously and update state in real-time
     * This ensures the UI is always in sync with the database
     */
    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoriteLaunchesUseCase()
                .catch { error ->
                    Log.e("LaunchesListViewModel", "Error observing favorites", error)
                }
                .collect { favorites ->
                    val favoriteIds = favorites.map { it.id }.toSet()

                    Log.d("LaunchesListViewModel", "Favorites updated: $favoriteIds")

                    _state.update { currentState ->
                        currentState.copy(favoriteLaunchIds = favoriteIds)
                    }
                }
        }
    }

    private fun toggleFavorite(launch: Launch) {
        viewModelScope.launch {
            Log.d("LaunchesListViewModel", "Toggling favorite for: ${launch.id}")

            val result = toggleFavoriteUseCase(launch)

            result.fold(
                onSuccess = { isNowFavorite ->
                    Log.d(
                        "LaunchesListViewModel",
                        "Toggle success - Is now favorite: $isNowFavorite"
                    )

                    if (isNowFavorite) {
                        _effect.emit(
                            LaunchesListEffect.ShowFavoriteAdded(
                                launch.missionName ?: "Launch"
                            )
                        )
                    } else {
                        _effect.emit(
                            LaunchesListEffect.ShowFavoriteRemoved(
                                launch.missionName ?: "Launch"
                            )
                        )
                    }
                },
                onFailure = { error ->
                    Log.e("LaunchesListViewModel", "Failed to toggle favorite", error)
                    _effect.emit(
                        LaunchesListEffect.ShowError(
                            error.message ?: "Failed to update favorite"
                        )
                    )
                }
            )
        }
    }

    private fun navigateToDetail(launchId: String) {
        viewModelScope.launch {
            _effect.emit(LaunchesListEffect.NavigateToDetail(launchId))
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke()
        }
    }
}
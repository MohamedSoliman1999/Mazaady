package com.example.mazaady.presentation.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazaady.domain.model.Launch
import com.example.mazaady.domain.useCase.GetFavoriteLaunchesUseCase
import com.example.mazaady.domain.useCase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteLaunchesUseCase: GetFavoriteLaunchesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<FavoritesEffect>()
    val effect = _effect.asSharedFlow()

    init {
        handleIntent(FavoritesIntent.LoadFavorites)
    }

    fun handleIntent(intent: FavoritesIntent) {
        when (intent) {
            is FavoritesIntent.LoadFavorites -> loadFavorites()
            is FavoritesIntent.OnLaunchClick -> navigateToDetail(intent.launchId)
            is FavoritesIntent.OnRemoveFavorite -> removeFavorite(intent.launch)
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getFavoriteLaunchesUseCase().collect { favorites ->
                _state.update {
                    it.copy(
                        favorites = favorites,
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }

    private fun removeFavorite(launch: Launch) {
        viewModelScope.launch {
            Log.d("FavoritesViewModel", "Removing favorite: ${launch.id}")

            val result = toggleFavoriteUseCase(launch)

            result.fold(
                onSuccess = {
                    _effect.emit(
                        FavoritesEffect.ShowRemoved(
                            launch.missionName ?: "Launch"
                        )
                    )
                },
                onFailure = { error ->
                    Log.e("FavoritesViewModel", "Failed to remove favorite", error)
                }
            )
        }
    }

    private fun navigateToDetail(launchId: String) {
        viewModelScope.launch {
            _effect.emit(FavoritesEffect.NavigateToDetail(launchId))
        }
    }
}
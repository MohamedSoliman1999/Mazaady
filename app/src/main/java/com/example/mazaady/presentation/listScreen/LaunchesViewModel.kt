package com.example.mazaady.presentation.listScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazaady.domain.useCase.GetLaunchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LaunchesListViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LaunchesListState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LaunchesListEffect>()
    val effect = _effect.asSharedFlow()

    init {
        handleIntent(LaunchesListIntent.LoadLaunches)
    }

    fun handleIntent(intent: LaunchesListIntent) {
        when (intent) {
            is LaunchesListIntent.LoadLaunches -> loadLaunches()
            is LaunchesListIntent.RetryLoad -> loadLaunches()
            is LaunchesListIntent.OnLaunchClick -> navigateToDetail(intent.launchId)
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

    private fun navigateToDetail(launchId: String) {
        viewModelScope.launch {
            _effect.emit(LaunchesListEffect.NavigateToDetail(launchId))
        }
    }
}
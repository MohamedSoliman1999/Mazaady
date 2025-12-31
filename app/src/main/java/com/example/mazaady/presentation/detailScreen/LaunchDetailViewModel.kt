package com.example.mazaady.presentation.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazaady.domain.useCase.GetLaunchDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LaunchDetailViewModel @Inject constructor(
    private val getLaunchDetailUseCase: GetLaunchDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LaunchDetailState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LaunchDetailEffect>()
    val effect = _effect.asSharedFlow()

    private var currentLaunchId: String? = null

    fun handleIntent(intent: LaunchDetailIntent) {
        when (intent) {
            is LaunchDetailIntent.LoadDetail -> {
                currentLaunchId = intent.id
                loadDetail(intent.id)
            }
            is LaunchDetailIntent.RetryLoad -> {
                currentLaunchId?.let { loadDetail(it) }
            }
            is LaunchDetailIntent.OnBackClick -> navigateBack()
        }
    }

    private fun loadDetail(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = getLaunchDetailUseCase(id)

            result.fold(
                onSuccess = { detail ->
                    _state.update {
                        it.copy(
                            launchDetail = detail,
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
                    _effect.emit(LaunchDetailEffect.ShowError(errorMessage))
                }
            )
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(LaunchDetailEffect.NavigateBack)
        }
    }
}
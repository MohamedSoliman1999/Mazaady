package com.example.mazaady.domain.useCase

import com.example.mazaady.domain.model.LaunchDetail
import com.example.mazaady.domain.repository.ILaunchRepository
import javax.inject.Inject

class GetLaunchDetailUseCase @Inject constructor(
    private val repository: ILaunchRepository
) {
    suspend operator fun invoke(id: String): Result<LaunchDetail> {
        return repository.getLaunchDetail(id)
    }
}
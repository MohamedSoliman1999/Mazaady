package com.example.mazaady.domain.useCase

import com.example.mazaady.domain.model.Launch
import com.example.mazaady.domain.repository.ILaunchRepository
import javax.inject.Inject

class GetLaunchesUseCase@Inject constructor(
    private val repository: ILaunchRepository
) {
    suspend operator fun invoke(): Result<List<Launch>> = repository.getLaunches()
}
package com.example.myapplicationtest.domain.useCase

import com.example.myapplicationtest.domain.model.Launch
import com.example.myapplicationtest.domain.repository.ILaunchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLaunchesUseCase@Inject constructor(
    private val repository: ILaunchRepository
) {
    suspend operator fun invoke(): Result<List<Launch>> = repository.getLaunches()
}
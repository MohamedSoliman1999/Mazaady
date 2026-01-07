package com.example.mazaady.data.repository
import com.example.mazaady.data.local.TokenManager
import com.example.mazaady.data.mapper.BookingMapper
import com.example.mazaady.data.remote.datasource.BookingRemoteDataSource
import com.example.mazaady.domain.model.BookingResult
import com.example.mazaady.domain.model.LoginResult
import com.example.mazaady.domain.repository.IBookingRepository
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class BookingRepository @Inject constructor(
    private val remoteDataSource: BookingRemoteDataSource,
    private val tokenManager: TokenManager // Inject TokenManager
) : IBookingRepository {

    override suspend fun login(email: String): Result<LoginResult> {
        return remoteDataSource.login(email)
            .mapCatching { data ->
                val loginResult = BookingMapper.toDomainModel(data.login)
                    ?: throw Exception("Login failed")

                // Save token after successful login
                tokenManager.saveToken(loginResult.token)

                loginResult
            }
    }

    override suspend fun bookTrips(launchIds: List<String>): Result<BookingResult> {
        // Check if user is authenticated
        if (!tokenManager.hasToken()) {
            return Result.failure(Exception("Please login first"))
        }

        return remoteDataSource.bookTrips(launchIds)
            .mapCatching { data ->
                BookingMapper.toDomainModel(data.bookTrips)
            }
    }

    override suspend fun cancelTrip(launchId: String): Result<BookingResult> {
        // Check if user is authenticated
        if (!tokenManager.hasToken()) {
            return Result.failure(Exception("Please login first"))
        }

        return remoteDataSource.cancelTrip(launchId)
            .mapCatching { data ->
                BookingMapper.toDomainModel(data.cancelTrip)
            }
    }
}
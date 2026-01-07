package com.example.mazaady.data.repository
import android.util.Log
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
    private val tokenManager: TokenManager
) : IBookingRepository {

    override suspend fun login(email: String): Result<LoginResult> {
        return remoteDataSource.login(email)
            .mapCatching { data ->
                val loginResult = BookingMapper.toDomainModel(data.login)
                    ?: throw Exception("Login failed - Invalid response")

                // Save authentication data securely
                tokenManager.saveToken(loginResult.token)
                tokenManager.saveUserId(loginResult.userId)
                tokenManager.saveUserEmail(email)

                Log.d("BookingRepository", "Login successful - Token saved to encrypted storage")

                loginResult
            }
    }

    override suspend fun bookTrips(launchIds: List<String>): Result<BookingResult> {
        // Verify authentication
        if (!tokenManager.isAuthenticated()) {
            return Result.failure(Exception("Authentication required. Please login again."))
        }

        Log.d("BookingRepository", "Booking trips with authenticated user")

        return remoteDataSource.bookTrips(launchIds)
            .mapCatching { data ->
                BookingMapper.toDomainModel(data.bookTrips)
            }
    }

    override suspend fun cancelTrip(launchId: String): Result<BookingResult> {
        // Verify authentication
        if (!tokenManager.isAuthenticated()) {
            return Result.failure(Exception("Authentication required. Please login again."))
        }

        return remoteDataSource.cancelTrip(launchId)
            .mapCatching { data ->
                BookingMapper.toDomainModel(data.cancelTrip)
            }
    }
}

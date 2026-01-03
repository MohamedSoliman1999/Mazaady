package com.example.mazaady.presentation.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mazaady.presentation.component.AppButton
import com.example.mazaady.presentation.component.AppTextField
import com.example.mazaady.presentation.component.AppTextFieldConfig
import com.example.mazaady.presentation.component.LaunchesTopBar
import com.example.mazaady.presentation.component.SuccessDialog
import com.example.mazaady.ui.theme.AppColors
import com.example.mazaady.ui.theme.LaunchesColorScheme
import com.example.mazaady.ui.theme.launchesColorScheme
import kotlinx.coroutines.flow.collectLatest

/**
 * Main composable for the Booking Screen
 * Handles booking trips with login and launch selection
 */
@Composable
fun BookingScreen(
    onBackClick: () -> Unit,
    viewModel: BookingViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val colors = launchesColorScheme()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is BookingEffect.NavigateBack -> onBackClick()
                is BookingEffect.ShowSuccess -> {
                    // Success is shown via dialog in UI
                }
                is BookingEffect.ShowError -> {
                    // Error is shown in UI state
                }
            }
        }
    }

    BookingScreenContent(
        state = state,
        colors = colors,
        onBackClick = onBackClick,
        onIntent = viewModel::handleIntent
    )
}

/**
 * Content composable for Booking Screen
 * Separated for better preview support
 */
@Composable
private fun BookingScreenContent(
    state: BookingState,
    colors: LaunchesColorScheme,
    onBackClick: () -> Unit,
    onIntent: (BookingIntent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
        ) {
            // Top Navigation Bar
            LaunchesTopBar(
                title = "Book Launch",
                backgroundColor = colors.topBar,
                contentColor = colors.onTopBar,
                onBackClick = onBackClick
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // Header with icon and title
                HeaderSection(
                    isLoggedIn = state.isLoggedIn,
                    colors = colors
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Main Form Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = colors.surface
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        if (!state.isLoggedIn) {
                            // Show login form
                            LoginForm(
                                email = state.email,
                                emailError = state.emailError,
                                isLoading = state.isLoading,
                                colors = colors,
                                onEmailChange = { onIntent(BookingIntent.OnEmailChange(it)) },
                                onLoginClick = { onIntent(BookingIntent.OnLoginClick) }
                            )
                        } else {
                            // Show booking form
                            BookingForm(
                                email = state.email,
                                launchIds = state.selectedLaunchIds.joinToString(", "),
                                isLoading = state.isLoading,
                                colors = colors,
                                onLaunchIdsChange = { onIntent(BookingIntent.OnLaunchIdChange(it)) },
                                onBookClick = { onIntent(BookingIntent.OnBookClick) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        // Success Dialog Overlay
        if (state.successMessage != null) {
            SuccessDialog(
                title = "Success!",
                message = state.successMessage,
                onDismiss = { onIntent(BookingIntent.OnDismissSuccess) },
                backgroundColor = colors.surface,
                textColor = colors.onSurface
            )
        }
    }
}

/**
 * Header section with emoji, title and description
 */
@Composable
private fun HeaderSection(
    isLoggedIn: Boolean,
    colors: LaunchesColorScheme
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Large emoji icon
        Text(
            text = "🚀",
            fontSize = 64.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Main title
        Text(
            text = if (isLoggedIn) "Book Your Space Journey" else "Login to Continue",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colors.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle/description
        Text(
            text = if (isLoggedIn) {
                "Enter launch IDs separated by commas"
            } else {
                "Enter your email to get started"
            },
            fontSize = 14.sp,
            color = colors.onSurfaceVariant
        )
    }
}

/**
 * Login form component
 * Shows email input and login button
 */
@Composable
private fun LoginForm(
    email: String,
    emailError: String?,
    isLoading: Boolean,
    colors:LaunchesColorScheme,
    onEmailChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column {
        // Field label
        Text(
            text = "Email Address",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Email input field
        AppTextField(
            value = email,
            onValueChange = onEmailChange,
            label = "Email",
            placeholder = "your.email@example.com",
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
            isError = emailError != null,
            errorMessage = emailError,
            enabled = !isLoading,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = colors.onSurfaceVariant
                )
            },
            config = AppTextFieldConfig(
                backgroundColor = colors.background,
                textColor = colors.onSurface,
                labelColor = colors.onSurfaceVariant
            ),
            onImeAction = onLoginClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Login button
        AppButton(
            text = "Login",
            onClick = onLoginClick,
            isLoading = isLoading,
            enabled = email.isNotEmpty() && !isLoading
        )
    }
}

/**
 * Booking form component
 * Shows logged in user info, launch ID input, and book button
 */
@Composable
private fun BookingForm(
    email: String,
    launchIds: String,
    isLoading: Boolean,
    colors: LaunchesColorScheme,
    onLaunchIdsChange: (String) -> Unit,
    onBookClick: () -> Unit
) {
    Column {
        // Logged in user info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = AppColors.IconCRS,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Logged in as:",
                    fontSize = 12.sp,
                    color = colors.onSurfaceVariant
                )
                Text(
                    text = email,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onSurface
                )
            }
        }

        // Launch IDs field label
        Text(
            text = "Launch IDs",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Launch IDs input field
        AppTextField(
            value = launchIds,
            onValueChange = onLaunchIdsChange,
            label = "Launch IDs",
            placeholder = "109, 110, 111",
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            enabled = !isLoading,
            maxLines = 2,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.RocketLaunch,
                    contentDescription = null,
                    tint = colors.onSurfaceVariant
                )
            },
            config = AppTextFieldConfig(
                backgroundColor = colors.background,
                textColor = colors.onSurface,
                labelColor = colors.onSurfaceVariant
            ),
            onImeAction = onBookClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Helper text with icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ℹ️",
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Separate multiple launch IDs with commas",
                fontSize = 12.sp,
                color = colors.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Book button
        AppButton(
            text = "Book Trips",
            onClick = onBookClick,
            isLoading = isLoading,
            enabled = launchIds.isNotEmpty() && !isLoading
        )
    }
}

// =============================================================================
// PREVIEW FUNCTIONS
// =============================================================================

@Preview(name = "Booking - Initial State", showSystemUi = true)
@Composable
private fun PreviewBookingScreenInitial() {
    BookingScreenContent(
        state = BookingState(
            email = "",
            isLoggedIn = false
        ),
        colors = launchesColorScheme(darkTheme = false),
        onBackClick = {},
        onIntent = {}
    )
}

@Preview(name = "Booking - Login with Email", showSystemUi = true)
@Composable
private fun PreviewBookingScreenLoginWithEmail() {
    BookingScreenContent(
        state = BookingState(
            email = "test@example.com",
            isLoggedIn = false
        ),
        colors = launchesColorScheme(darkTheme = false),
        onBackClick = {},
        onIntent = {}
    )
}

@Preview(name = "Booking - Login Error", showSystemUi = true)
@Composable
private fun PreviewBookingScreenLoginError() {
    BookingScreenContent(
        state = BookingState(
            email = "invalid",
            emailError = "Invalid email format",
            isLoggedIn = false
        ),
        colors = launchesColorScheme(darkTheme = false),
        onBackClick = {},
        onIntent = {}
    )
}

@Preview(name = "Booking - Login Loading", showSystemUi = true)
@Composable
private fun PreviewBookingScreenLoginLoading() {
    BookingScreenContent(
        state = BookingState(
            email = "test@example.com",
            isLoggedIn = false,
            isLoading = true
        ),
        colors = launchesColorScheme(darkTheme = false),
        onBackClick = {},
        onIntent = {}
    )
}

@Preview(name = "Booking - Logged In", showSystemUi = true)
@Composable
private fun PreviewBookingScreenLoggedIn() {
    BookingScreenContent(
        state = BookingState(
            email = "test@example.com",
            isLoggedIn = true,
            selectedLaunchIds = emptyList()
        ),
        colors = launchesColorScheme(darkTheme = false),
        onBackClick = {},
        onIntent = {}
    )
}

@Preview(name = "Booking - With Launch IDs", showSystemUi = true)
@Composable
private fun PreviewBookingScreenWithLaunchIds() {
    BookingScreenContent(
        state = BookingState(
            email = "test@example.com",
            isLoggedIn = true,
            selectedLaunchIds = listOf("109", "110")
        ),
        colors = launchesColorScheme(darkTheme = false),
        onBackClick = {},
        onIntent = {}
    )
}

@Preview(name = "Booking - Booking Loading", showSystemUi = true)
@Composable
private fun PreviewBookingScreenBookingLoading() {
    BookingScreenContent(
        state = BookingState(
            email = "test@example.com",
            isLoggedIn = true,
            selectedLaunchIds = listOf("109", "110", "111"),
            isLoading = true
        ),
        colors = launchesColorScheme(darkTheme = false),
        onBackClick = {},
        onIntent = {}
    )
}

@Preview(name = "Booking - Dark Mode", showSystemUi = true)
@Composable
private fun PreviewBookingScreenDark() {
    BookingScreenContent(
        state = BookingState(
            email = "john.doe@spacex.com",
            isLoggedIn = true,
            selectedLaunchIds = listOf("109", "110", "111")
        ),
        colors = launchesColorScheme(darkTheme = true),
        onBackClick = {},
        onIntent = {}
    )
}

@Preview(name = "Booking - Success Dialog", showSystemUi = true)
@Composable
private fun PreviewBookingScreenSuccess() {
    BookingScreenContent(
        state = BookingState(
            email = "test@example.com",
            isLoggedIn = true,
            selectedLaunchIds = listOf("109", "110"),
            successMessage = "Successfully booked 2 launches!"
        ),
        colors = launchesColorScheme(darkTheme = false),
        onBackClick = {},
        onIntent = {}
    )
}

@Preview(name = "Booking - Multiple Launch IDs", showSystemUi = true)
@Composable
private fun PreviewBookingScreenMultipleLaunches() {
    BookingScreenContent(
        state = BookingState(
            email = "astronaut@nasa.gov",
            isLoggedIn = true,
            selectedLaunchIds = listOf("109", "110", "111", "112", "113")
        ),
        colors = launchesColorScheme(darkTheme = false),
        onBackClick = {},
        onIntent = {}
    )
}
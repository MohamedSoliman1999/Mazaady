package com.example.mazaady.presentation.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Flight
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
import androidx.compose.ui.graphics.Color
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
                is BookingEffect.ShowSuccess -> {}
                is BookingEffect.ShowError -> {}
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

@Composable
private fun BookingScreenContent(
    state: BookingState,
    colors:LaunchesColorScheme,
    onBackClick: () -> Unit,
    onIntent: (BookingIntent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
        ) {
            LaunchesTopBar(
                title = "Book Launch",
                backgroundColor = colors.topBar,
                contentColor = colors.onTopBar,
                onBackClick = onBackClick
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // Header
                Text(
                    text = "🚀",
                    fontSize = 64.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (state.isLoggedIn) "Book Your Space Journey" else "Login to Continue",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (state.isLoggedIn) {
                        "Enter launch IDs separated by commas"
                    } else {
                        "Enter your email to get started"
                    },
                    fontSize = 14.sp,
                    color = colors.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Error Message Display (NEW)
                if (state.error != null && state.error.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Red.copy(alpha = 0.1f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "❌", fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = state.error,
                                color = Color.Red,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                // Form Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = colors.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        if (!state.isLoggedIn) {
                            // Login Form
                            LoginFormSection(
                                email = state.email,
                                emailError = state.emailError,
                                isLoading = state.isLoading,
                                colors = colors,
                                onEmailChange = { onIntent(BookingIntent.OnEmailChange(it)) },
                                onLoginClick = { onIntent(BookingIntent.OnLoginClick) }
                            )
                        } else {
                            // Booking Form
                            BookingFormSection(
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

        // Success Dialog
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

@Composable
private fun LoginFormSection(
    email: String,
    emailError: String?,
    isLoading: Boolean,
    colors: LaunchesColorScheme,
    onEmailChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column {
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
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            },
            config = AppTextFieldConfig(
                backgroundColor = colors.background,
                textColor = colors.onSurface,
                labelColor = colors.onSurfaceVariant
            ),
            onImeAction = onLoginClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        AppButton(
            text = "Login",
            onClick = onLoginClick,
            isLoading = isLoading,
            enabled = email.isNotEmpty()
        )
    }
}

@Composable
private fun BookingFormSection(
    email: String,
    launchIds: String,
    isLoading: Boolean,
    colors: LaunchesColorScheme,
    onLaunchIdsChange: (String) -> Unit,
    onBookClick: () -> Unit
) {
    Column {
        // Logged in info
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
                Text(text = "Logged in as:", fontSize = 12.sp, color = colors.onSurfaceVariant)
                Text(text = email, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = colors.onSurface)
            }
        }

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
                Icon(imageVector = Icons.Default.Flight, contentDescription = null)
            },
            config = AppTextFieldConfig(
                backgroundColor = colors.background,
                textColor = colors.onSurface,
                labelColor = colors.onSurfaceVariant
            ),
            onImeAction = onBookClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "ℹ️ Separate multiple IDs with commas",
            fontSize = 12.sp,
            color = colors.onSurfaceVariant,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        AppButton(
            text = "Book Trips",
            onClick = onBookClick,
            isLoading = isLoading,
            enabled = launchIds.isNotEmpty()
        )
    }
}
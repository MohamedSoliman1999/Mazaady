package com.example.mazaady.presentation.listScreen

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mazaady.domain.model.Launch
import com.example.mazaady.presentation.component.LaunchesTopBar
import com.example.mazaady.ui.theme.AppColors
import com.example.mazaady.ui.theme.LaunchesColorScheme
import com.example.mazaady.ui.theme.launchesColorScheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mazaady.presentation.component.EmptyStateView
import com.example.mazaady.presentation.component.ErrorView
import com.example.mazaady.presentation.component.LaunchListItem
import com.example.mazaady.presentation.component.LoadingIndicator
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LaunchesListScreen(
    onLaunchClick: (String) -> Unit,
    onBookingClick: () -> Unit,
    viewModel: LaunchesListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val colors = launchesColorScheme()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is LaunchesListEffect.NavigateToDetail -> {
                    onLaunchClick(effect.launchId)
                }
                is LaunchesListEffect.ShowError -> {
                    // Handle error - could show snackbar
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.logout()
                    onBookingClick.invoke()
                },
                containerColor = AppColors.IconCRS,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Book Launch",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { paddingValues ->
        LaunchesListContent(
            state = state,
            colors = colors,
            paddingValues = paddingValues,
            onLaunchClick = { launchId ->
                viewModel.handleIntent(LaunchesListIntent.OnLaunchClick(launchId))
            },
            onRetry = {
                viewModel.handleIntent(LaunchesListIntent.RetryLoad)
            }
        )
    }
}

@Composable
private fun LaunchesListContent(
    state: LaunchesListState,
    colors: LaunchesColorScheme,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onLaunchClick: (String) -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        LaunchesTopBar(
            modifier = Modifier.height(100.dp),
            title = "Launches",
            backgroundColor = colors.topBar,
            contentColor = colors.onTopBar,
            isBackButtonEnabled = false,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            isWideTopBar=true
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    LoadingIndicator(color = colors.onSurface)
                }
                state.error != null -> {
                    ErrorView(
                        message = state.error,
                        onRetry = onRetry,
                        textColor = colors.onSurface
                    )
                }
                state.launches.isEmpty() -> {
                    EmptyStateView(
                        message = "No launches available",
                        textColor = colors.onSurface
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 80.dp // Extra padding for FAB
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = state.launches,
                            key = { it.id }
                        ) { launch ->
                            LaunchListItem(
                                launch = launch,
                                backgroundColor = colors.surface,
                                titleColor = colors.onSurface,
                                subtitleColor = colors.onSurfaceVariant,
                                onClick = { onLaunchClick(launch.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

// Preview
@Preview(name = "Launches List with FAB", showSystemUi = true)
@Composable
private fun PreviewLaunchesListWithFAB() {
    val sampleState = LaunchesListState(
        launches = listOf(
            Launch("1", "KSC LC 39A", "CRS-21", null, "Falcon 9", "FT"),
            Launch("2", "CCAFS SLC 40", "Starlink-15", null, "Falcon 9", "FT"),
            Launch("3", "KSC LC 39A", "GPS III SV04", null, "Falcon 9", "FT")
        )
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = AppColors.IconCRS,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Book Launch"
                )
            }
        }
    ) { paddingValues ->
        LaunchesListContent(
            state = sampleState,
            colors = launchesColorScheme(darkTheme = false),
            paddingValues = paddingValues,
            onLaunchClick = {},
            onRetry = {}
        )
    }
}
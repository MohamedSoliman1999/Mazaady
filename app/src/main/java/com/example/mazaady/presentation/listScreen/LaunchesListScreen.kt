package com.example.mazaady.presentation.listScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mazaady.domain.model.Launch
import com.example.mazaady.presentation.component.EmptyStateView
import com.example.mazaady.presentation.component.ErrorView
import com.example.mazaady.presentation.component.LaunchListItem
import com.example.mazaady.presentation.component.LaunchesTopBar
import com.example.mazaady.presentation.component.LoadingIndicator
import com.example.mazaady.ui.theme.AppColors
import com.example.mazaady.ui.theme.LaunchesColorScheme
import com.example.mazaady.ui.theme.launchesColorScheme
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LaunchesListScreen(
    onLaunchClick: (String) -> Unit,
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

    LaunchesListContent(
        state = state,
        colors = colors,
        onLaunchClick = { launchId ->
            viewModel.handleIntent(LaunchesListIntent.OnLaunchClick(launchId))
        },
        onRetry = {
            viewModel.handleIntent(LaunchesListIntent.RetryLoad)
        }
    )
}

@Composable
private fun LaunchesListContent(
    state: LaunchesListState,
    colors: LaunchesColorScheme,
    onLaunchClick: (String) -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        LaunchesTopBar(
            title = "Launches",
            backgroundColor = AppColors.Black,
            titleColor = AppColors.White
        )

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
                    contentPadding = PaddingValues(16.dp),
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

@Preview(name = "Launches List - Light with Data", showSystemUi = true)
@Composable
private fun PreviewLaunchesListLight() {
    val sampleState = LaunchesListState(
        launches = listOf(
            Launch("1", "KSC LC 39A", "CRS-21", null, "Falcon 9", "FT"),
            Launch("2", "CCAFS SLC 40", "Starlink-15", null, "Falcon 9", "FT"),
            Launch("3", "KSC LC 39A", "GPS III SV04", null, "Falcon 9", "FT")
        )
    )

    LaunchesListContent(
        state = sampleState,
        colors = launchesColorScheme(darkTheme = false),
        onLaunchClick = {},
        onRetry = {}
    )
}

@Preview(name = "Launches List - Dark with Data", showSystemUi = true)
@Composable
private fun PreviewLaunchesListDark() {
    val sampleState = LaunchesListState(
        launches = listOf(
            Launch("1", "KSC LC 39A", "CRS-21", null, "Falcon 9", "FT"),
            Launch("2", "CCAFS SLC 40", "Starlink-15", null, "Falcon 9", "FT")
        )
    )

    LaunchesListContent(
        state = sampleState,
        colors = launchesColorScheme(darkTheme = true),
        onLaunchClick = {},
        onRetry = {}
    )
}

@Preview(name = "Launches List - Loading", showSystemUi = true)
@Composable
private fun PreviewLaunchesListLoading() {
    LaunchesListContent(
        state = LaunchesListState(isLoading = true),
        colors = launchesColorScheme(darkTheme = false),
        onLaunchClick = {},
        onRetry = {}
    )
}

@Preview(name = "Launches List - Error", showSystemUi = true)
@Composable
private fun PreviewLaunchesListError() {
    LaunchesListContent(
        state = LaunchesListState(error = "Failed to load launches. Please check your connection."),
        colors = launchesColorScheme(darkTheme = false),
        onLaunchClick = {},
        onRetry = {}
    )
}

@Preview(name = "Launches List - Empty", showSystemUi = true)
@Composable
private fun PreviewLaunchesListEmpty() {
    LaunchesListContent(
        state = LaunchesListState(launches = emptyList()),
        colors = launchesColorScheme(darkTheme = false),
        onLaunchClick = {},
        onRetry = {}
    )
}
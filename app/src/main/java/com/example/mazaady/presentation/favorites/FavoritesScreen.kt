package com.example.mazaady.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.mazaady.ui.theme.LaunchesColorScheme
import com.example.mazaady.ui.theme.launchesColorScheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FavoritesScreen(
    onLaunchClick: (String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val colors = launchesColorScheme()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is FavoritesEffect.NavigateToDetail -> {
                    onLaunchClick(effect.launchId)
                }
                is FavoritesEffect.ShowRemoved -> {
                    snackbarHostState.showSnackbar(
                        message = "${effect.launchName} removed from favorites"
                    )
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        FavoritesScreenContent(
            state = state,
            colors = colors,
            onBackClick = onBackClick,
            onLaunchClick = { launchId ->
                viewModel.handleIntent(FavoritesIntent.OnLaunchClick(launchId))
            },
            onRemoveFavorite = { launch ->
                viewModel.handleIntent(FavoritesIntent.OnRemoveFavorite(launch))
            }
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun FavoritesScreenContent(
    state: FavoritesState,
    colors: LaunchesColorScheme,
    onBackClick: () -> Unit,
    onLaunchClick: (String) -> Unit,
    onRemoveFavorite: (Launch) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        LaunchesTopBar(
            title = "Favorite Launches",
            backgroundColor = colors.topBar,
            contentColor = colors.onTopBar,
            onBackClick = onBackClick
        )

        when {
            state.isLoading -> {
                LoadingIndicator(color = colors.onSurface)
            }
            state.error != null -> {
                ErrorView(
                    message = state.error,
                    onRetry = {},
                    textColor = colors.onSurface
                )
            }
            state.favorites.isEmpty() -> {
                EmptyStateView(
                    message = "No favorite launches yet.\nTap ❤️ on any launch to add it here!",
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
                        items = state.favorites,
                        key = { it.id }
                    ) { launch ->
                        LaunchListItem(
                            launch = launch,
                            isFavorite = true,
                            backgroundColor = colors.surface,
                            titleColor = colors.onSurface,
                            subtitleColor = colors.onSurfaceVariant,
                            onClick = { onLaunchClick(launch.id) },
                            onFavoriteClick = { onRemoveFavorite(launch) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Favorites Screen - With Data", showSystemUi = true)
@Composable
private fun PreviewFavoritesScreenWithData() {
    val sampleFavorites = listOf(
        Launch("1", "KSC LC 39A", "CRS-21", null, "Falcon 9", "FT"),
        Launch("2", "CCAFS SLC 40", "Starlink-15", null, "Falcon 9", "FT")
    )

    FavoritesScreenContent(
        state = FavoritesState(favorites = sampleFavorites),
        colors = launchesColorScheme(darkTheme = false),
        onBackClick = {},
        onLaunchClick = {},
        onRemoveFavorite = {}
    )
}

@Preview(name = "Favorites Screen - Empty", showSystemUi = true)
@Composable
private fun PreviewFavoritesScreenEmpty() {
    FavoritesScreenContent(
        state = FavoritesState(favorites = emptyList()),
        colors = launchesColorScheme(darkTheme = false),
        onBackClick = {},
        onLaunchClick = {},
        onRemoveFavorite = {}
    )
}

@Preview(name = "Favorites Screen - Dark", showSystemUi = true)
@Composable
private fun PreviewFavoritesScreenDark() {
    val sampleFavorites = listOf(
        Launch("1", "KSC LC 39A", "CRS-21", null, "Falcon 9", "FT")
    )

    FavoritesScreenContent(
        state = FavoritesState(favorites = sampleFavorites),
        colors = launchesColorScheme(darkTheme = true),
        onBackClick = {},
        onLaunchClick = {},
        onRemoveFavorite = {}
    )
}
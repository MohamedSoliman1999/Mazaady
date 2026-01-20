package com.example.mazaady.presentation.listScreen

import com.example.mazaady.domain.model.Launch
import com.example.mazaady.presentation.component.LaunchesTopBar
import com.example.mazaady.ui.theme.AppColors
import com.example.mazaady.ui.theme.LaunchesColorScheme
import com.example.mazaady.ui.theme.launchesColorScheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mazaady.presentation.component.EmptyStateView
import com.example.mazaady.presentation.component.ErrorView
import com.example.mazaady.presentation.component.LaunchListItem
import com.example.mazaady.presentation.component.LoadingIndicator
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LaunchesListScreen(
    onLaunchClick: (String) -> Unit,
    onBookingClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    viewModel: LaunchesListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val colors = launchesColorScheme()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is LaunchesListEffect.NavigateToDetail -> {
                    onLaunchClick(effect.launchId)
                }
                is LaunchesListEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is LaunchesListEffect.ShowFavoriteAdded -> {
                    snackbarHostState.showSnackbar(
                        message = "❤️ ${effect.launchName} added to favorites",
                        duration = SnackbarDuration.Short
                    )
                }
                is LaunchesListEffect.ShowFavoriteRemoved -> {
                    snackbarHostState.showSnackbar(
                        message = "💔 ${effect.launchName} removed from favorites",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingActionButton(
                    onClick = onFavoritesClick,
                    containerColor = Color.Red,
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "View Favorites",
                        modifier = Modifier.size(24.dp)
                    )
                }

                FloatingActionButton(
                    onClick = onBookingClick,
                    containerColor = AppColors.IconCRS,
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Book Launch",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 80.dp)
            )
        }
    ) { paddingValues ->
        LaunchesListContent(
            state = state,
            colors = colors,
            paddingValues = paddingValues,
            onLaunchClick = { launchId ->
                viewModel.handleIntent(LaunchesListIntent.OnLaunchClick(launchId))
            },
            onFavoriteClick = { launch ->
                viewModel.handleIntent(LaunchesListIntent.OnFavoriteClick(launch))
            },
            onRetry = {
                viewModel.handleIntent(LaunchesListIntent.RetryLoad)
            }
        )
    }
}

/**
 * OPTIMIZED: Uses derivedStateOf to prevent unnecessary recompositions
 */
@Composable
private fun LaunchesListContent(
    state: LaunchesListState,
    colors: LaunchesColorScheme,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onLaunchClick: (String) -> Unit,
    onFavoriteClick: (Launch) -> Unit,
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
                    OptimizedLaunchList(
                        launches = state.launches,
                        favoriteLaunchIds = state.favoriteLaunchIds,
                        colors = colors,
                        onLaunchClick = onLaunchClick,
                        onFavoriteClick = onFavoriteClick
                    )
                }
            }
        }
    }
}

/**
 * CRITICAL OPTIMIZATION: Separate composable for the list
 * Uses key parameter to ensure only changed items recompose
 */
@Composable
private fun OptimizedLaunchList(
    launches: List<Launch>,
    favoriteLaunchIds: Set<String>,
    colors: LaunchesColorScheme,
    onLaunchClick: (String) -> Unit,
    onFavoriteClick: (Launch) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 160.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = launches,
            key = { launch -> launch.id } // CRITICAL: Unique key for each item
        ) { launch ->
            // OPTIMIZATION: Only reads favorite status for THIS item
            val isFavorite = favoriteLaunchIds.contains(launch.id)

            // Only THIS item recomposes when its favorite status changes
            LaunchListItem(
                launch = launch,
                isFavorite = isFavorite,
                backgroundColor = colors.surface,
                titleColor = colors.onSurface,
                subtitleColor = colors.onSurfaceVariant,
                onClick = { onLaunchClick(launch.id) },
                onFavoriteClick = { onFavoriteClick(launch) }
            )
        }
    }
}

@Preview(name = "Optimized Launches List", showSystemUi = true)
@Composable
private fun PreviewOptimizedLaunchesList() {
    val sampleState = LaunchesListState(
        launches = listOf(
            Launch("1", "KSC LC 39A", "CRS-21", null, "Falcon 9", "FT"),
            Launch("2", "CCAFS SLC 40", "Starlink-15", null, "Falcon 9", "FT"),
            Launch("3", "KSC LC 39A", "GPS III", null, "Falcon 9", "FT")
        ),
        favoriteLaunchIds = setOf("1")
    )

    Scaffold(
        floatingActionButton = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                FloatingActionButton(onClick = {}, containerColor = Color.Red) {
                    Icon(Icons.Default.Favorite, "Favorites")
                }
                FloatingActionButton(onClick = {}, containerColor = AppColors.IconCRS) {
                    Icon(Icons.Default.AddCircle, "Book")
                }
            }
        }
    ) { paddingValues ->
        LaunchesListContent(
            state = sampleState,
            colors = launchesColorScheme(darkTheme = false),
            paddingValues = paddingValues,
            onLaunchClick = {},
            onFavoriteClick = {},
            onRetry = {}
        )
    }
}


@Preview(name = "Launches with Favorites", showSystemUi = true)
@Composable
private fun PreviewLaunchesListWithFavorites() {
    val sampleState = LaunchesListState(
        launches = listOf(
            Launch("1", "KSC LC 39A", "CRS-21", null, "Falcon 9", "FT"),
            Launch("2", "CCAFS SLC 40", "Starlink-15", null, "Falcon 9", "FT"),
            Launch("3", "KSC LC 39A", "GPS III", null, "Falcon 9", "FT")
        ),
        favoriteLaunchIds = setOf("1", "3") // First and third are favorites
    )

    Scaffold(
        floatingActionButton = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                FloatingActionButton(onClick = {}, containerColor = Color.Red) {
                    Icon(Icons.Default.Favorite, "Favorites")
                }
                FloatingActionButton(onClick = {}, containerColor = AppColors.IconCRS) {
                    Icon(Icons.Default.AddCircle, "Book")
                }
            }
        }
    ) { paddingValues ->
        LaunchesListContent(
            state = sampleState,
            colors = launchesColorScheme(darkTheme = false),
            paddingValues = paddingValues,
            onLaunchClick = {},
            onFavoriteClick = {},
            onRetry = {}
        )
    }
}
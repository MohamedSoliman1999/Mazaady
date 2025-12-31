package com.example.mazaady.presentation.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mazaady.domain.model.LaunchDetail
import com.example.mazaady.presentation.component.LaunchesTopBar
import com.example.mazaady.presentation.component.ErrorView
import com.example.mazaady.presentation.component.InfoSection
import com.example.mazaady.presentation.component.LoadingIndicator
import com.example.mazaady.presentation.component.MissionPatch
import com.example.mazaady.ui.theme.LaunchesColorScheme
import com.example.mazaady.ui.theme.launchesColorScheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LaunchDetailScreen(
    launchId: String,
    onBackClick: () -> Unit,
    viewModel: LaunchDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val colors = launchesColorScheme()

    LaunchedEffect(launchId) {
        viewModel.handleIntent(LaunchDetailIntent.LoadDetail(launchId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is LaunchDetailEffect.NavigateBack -> onBackClick()
                is LaunchDetailEffect.ShowError -> {
                    // Handle error - could show snackbar
                }
            }
        }
    }

    LaunchDetailContent(
        state = state,
        colors = colors,
        launchId = launchId,
        onBackClick = {
            viewModel.handleIntent(LaunchDetailIntent.OnBackClick)
        },
        onRetry = {
            viewModel.handleIntent(LaunchDetailIntent.RetryLoad)
        }
    )
}

@Composable
private fun LaunchDetailContent(
    state: LaunchDetailState,
    colors: LaunchesColorScheme,
    launchId: String,
    onBackClick: () -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        LaunchesTopBar(
            modifier = Modifier.wrapContentHeight(),
            title = "Launch Detail (id: $launchId)",
            backgroundColor = colors.topBar,
            contentColor = colors.onTopBar,
            onBackClick = onBackClick,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
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

            state.launchDetail != null -> {
                DetailContentBody(
                    detail = state.launchDetail,
                    colors = colors
                )
            }
        }
    }
}

@Composable
private fun DetailContentBody(
    detail: LaunchDetail,
    colors: LaunchesColorScheme
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Mission Patch
        MissionPatch(patchUrl = detail.missionPatch)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))


            Spacer(modifier = Modifier.height(32.dp))

            // Rocket Info
            InfoSection(
                title = "Rocket",
                items = buildList {
                    detail.rocketName?.let { add("NAME" to it) }
                    detail.rocketType?.let { add("TYPE" to it) }
                    detail.rocketId?.let { add("ID" to it) }
                },
                titleColor = colors.onSurface,
                labelColor = colors.onSurfaceVariant,
                valueColor = colors.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Mission Info
            detail.missionName?.let { missionName ->
                InfoSection(
                    title = "Mission:",
                    items = listOf("NAME" to missionName),
                    titleColor = colors.onSurface,
                    labelColor = colors.onSurfaceVariant,
                    valueColor = colors.onSurface
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Site Info
            detail.site?.let { site ->
                InfoSection(
                    title = "Site:",
                    items = listOf("" to site),
                    titleColor = colors.onSurface,
                    labelColor = colors.onSurfaceVariant,
                    valueColor = colors.onSurface
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Booking Status
            InfoSection(
                title = "Status:",
                items = listOf("BOOKED" to if (detail.isBooked) "Yes" else "No"),
                titleColor = colors.onSurface,
                labelColor = colors.onSurfaceVariant,
                valueColor = colors.onSurface
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

}

@Preview(name = "Launch Detail - Light with Data", showSystemUi = true)
@Composable
private fun PreviewLaunchDetailLight() {
    val sampleDetail = LaunchDetail(
        id = "110",
        site = "KSC LC 39A",
        missionName = "CRS-21",
        missionPatch = null,
        rocketId = "falcon9",
        rocketName = "Falcon 9",
        rocketType = "FT",
        isBooked = false
    )

    LaunchDetailContent(
        state = LaunchDetailState(launchDetail = sampleDetail),
        colors = launchesColorScheme(darkTheme = false),
        launchId = "110",
        onBackClick = {},
        onRetry = {}
    )
}

@Preview(name = "Launch Detail - Dark with Data", showSystemUi = true)
@Composable
private fun PreviewLaunchDetailDark() {
    val sampleDetail = LaunchDetail(
        id = "110",
        site = "KSC LC 39A",
        missionName = "CRS-21",
        missionPatch = null,
        rocketId = "falcon9",
        rocketName = "Falcon 9",
        rocketType = "FT",
        isBooked = true
    )

    LaunchDetailContent(
        state = LaunchDetailState(launchDetail = sampleDetail),
        colors = launchesColorScheme(darkTheme = true),
        launchId = "110",
        onBackClick = {},
        onRetry = {}
    )
}

@Preview(name = "Launch Detail - Loading", showSystemUi = true)
@Composable
private fun PreviewLaunchDetailLoading() {
    LaunchDetailContent(
        state = LaunchDetailState(isLoading = true),
        colors = launchesColorScheme(darkTheme = false),
        launchId = "110",
        onBackClick = {},
        onRetry = {}
    )
}

@Preview(name = "Launch Detail - Error", showSystemUi = true)
@Composable
private fun PreviewLaunchDetailError() {
    LaunchDetailContent(
        state = LaunchDetailState(error = "Failed to load launch details"),
        colors = launchesColorScheme(darkTheme = false),
        launchId = "110",
        onBackClick = {},
        onRetry = {}
    )
}
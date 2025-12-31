package com.example.mazaady.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mazaady.ui.theme.AppColors
@Composable
fun LaunchIcon(
    missionPatch: String?,
    rocketName: String?,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(AppColors.IconCRS),
        contentAlignment = Alignment.Center
    ) {
        if (!missionPatch.isNullOrEmpty()) {
            AsyncImage(
                model = missionPatch,
                contentDescription = "Mission patch",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(size)
            )
        } else {
            // Fallback icon
            Text(
                text = "🚀",
                fontSize = (size.value * 0.5f).sp
            )
        }
    }
}

@Preview(name = "Launch Icon with Image", showBackground = true)
@Composable
private fun PreviewLaunchIconWithImage() {
    Box(modifier = Modifier.padding(16.dp)) {
        LaunchIcon(
            missionPatch = "https://example.com/patch.png",
            rocketName = "Falcon 9",
            size = 48.dp
        )
    }
}

@Preview(name = "Launch Icon Fallback", showBackground = true)
@Composable
private fun PreviewLaunchIconFallback() {
    Box(modifier = Modifier.padding(16.dp)) {
        LaunchIcon(
            missionPatch = null,
            rocketName = "Falcon 9",
            size = 48.dp
        )
    }
}
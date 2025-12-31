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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mazaady.ui.theme.AppColors

@Composable
fun MissionPatch(
    patchUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(280.dp)
            .clip(CircleShape)
            .background(AppColors.IconCRS),
        contentAlignment = Alignment.Center
    ) {
        if (!patchUrl.isNullOrEmpty()) {
            AsyncImage(
                model = patchUrl,
                contentDescription = "Mission patch",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(280.dp)
            )
        } else {
            Text(
                text = "🚀",
                fontSize = 120.sp
            )
        }
    }
}

@Preview(name = "Mission Patch with URL", showBackground = true)
@Composable
private fun PreviewMissionPatchWithUrl() {
    Box(modifier = Modifier.padding(20.dp)) {
        MissionPatch(patchUrl = "https://example.com/patch.png")
    }
}

@Preview(name = "Mission Patch Fallback", showBackground = true)
@Composable
private fun PreviewMissionPatchFallback() {
    Box(modifier = Modifier.padding(20.dp)) {
        MissionPatch(patchUrl = null)
    }
}
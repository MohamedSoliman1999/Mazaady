package com.example.mazaady.presentation.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.mazaady.domain.model.Launch
import com.example.mazaady.ui.theme.AppColors
@Composable
fun LaunchListItem(
    launch: Launch,
    isFavorite: Boolean,
    backgroundColor: Color,
    titleColor: Color,
    subtitleColor: Color,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.e("LaunchListItem: ${launch.id}","###LaunchListItem recomposed")
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LaunchIcon(
            missionPatch = launch.missionPatch,
            rocketName = launch.rocketName,
            size = 48.dp
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = launch.rocketName ?: "Unknown Rocket",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = titleColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = launch.missionName ?: "Unknown Mission",
                fontSize = 14.sp,
                color = subtitleColor,
                maxLines = 1
            )
        }

        // Favorite Button - Isolated recomposition
        FavoriteButton(
            isFavorite = isFavorite,
            subtitleColor = subtitleColor,
            onClick = onFavoriteClick
        )
    }
}

/**
 * OPTIMIZATION: Separate composable for favorite button
 * Only this recomposes when favorite state changes
 */
@Composable
private fun FavoriteButton(
    isFavorite: Boolean,
    subtitleColor: Color,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(40.dp)
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) Color.Red else subtitleColor,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(name = "Launch Item - Favorite", showBackground = true)
@Composable
private fun PreviewLaunchItemFavorite() {
    Box(
        modifier = Modifier
            .background(AppColors.LightBackground)
            .padding(16.dp)
    ) {
        LaunchListItem(
            launch = Launch("1", "KSC LC 39A", "CRS-21", null, "Falcon 9", "FT"),
            isFavorite = true,
            backgroundColor = AppColors.LightSurface,
            titleColor = AppColors.LightOnSurface,
            subtitleColor = AppColors.LightOnSurfaceVariant,
            onClick = {},
            onFavoriteClick = {}
        )
    }
}

@Preview(name = "Launch Item - Not Favorite", showBackground = true)
@Composable
private fun PreviewLaunchItemNotFavorite() {
    Box(
        modifier = Modifier
            .background(AppColors.LightBackground)
            .padding(16.dp)
    ) {
        LaunchListItem(
            launch = Launch("1", "KSC LC 39A", "CRS-21", null, "Falcon 9", "FT"),
            isFavorite = false,
            backgroundColor = AppColors.LightSurface,
            titleColor = AppColors.LightOnSurface,
            subtitleColor = AppColors.LightOnSurfaceVariant,
            onClick = {},
            onFavoriteClick = {}
        )
    }
}
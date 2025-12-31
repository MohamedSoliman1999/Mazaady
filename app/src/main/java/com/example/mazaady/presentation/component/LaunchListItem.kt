package com.example.mazaady.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
    backgroundColor: Color,
    titleColor: Color,
    subtitleColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
    }
}

@Preview(name = "Launch Item Light", showBackground = true)
@Composable
private fun PreviewLaunchItemLight() {
    Box(
        modifier = Modifier.background(AppColors.LightBackground)
            .padding(16.dp)
    ) {
        LaunchListItem(
            launch = Launch(
                id = "1",
                site = "KSC LC 39A",
                missionName = "Crew Dragon Demo-2",
                missionPatch = null,
                rocketName = "Falcon 9",
                rocketType = "FT"
            ),
            backgroundColor = AppColors.LightSurface,
            titleColor = AppColors.LightOnSurface,
            subtitleColor = AppColors.LightOnSurfaceVariant,
            onClick = {}
        )
    }
}

@Preview(name = "Launch Item Dark", showBackground = true, backgroundColor = 0xFF1C1C1E)
@Composable
private fun PreviewLaunchItemDark() {
    Box(
        modifier = Modifier.background(AppColors.DarkBackground)
            .padding(16.dp)
    ) {
        LaunchListItem(
            launch = Launch(
                id = "1",
                site = "KSC LC 39A",
                missionName = "Starlink-15",
                missionPatch = null,
                rocketName = "Falcon 9",
                rocketType = "FT"
            ),
            backgroundColor = AppColors.DarkSurface,
            titleColor = AppColors.DarkOnSurface,
            subtitleColor = AppColors.DarkOnSurfaceVariant,
            onClick = {}
        )
    }
}
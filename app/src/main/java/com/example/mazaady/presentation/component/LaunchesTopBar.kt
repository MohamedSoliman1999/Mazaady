package com.example.mazaady.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import com.example.mazaady.ui.theme.AppColors
@Composable
fun LaunchesTopBar(
    title: String,
    backgroundColor: Color,
    titleColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(backgroundColor)
    ) {
        // Status bar spacer with indicator dot
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .background(AppColors.IndicatorDot, CircleShape)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Title
        Text(
            text = title,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = titleColor,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
    }
}

@Preview(name = "Top Bar Light", showBackground = true)
@Composable
private fun PreviewTopBarLight() {
    LaunchesTopBar(
        title = "Launches",
        backgroundColor = AppColors.Black,
        titleColor = AppColors.White
    )
}

@Preview(name = "Top Bar Dark", showBackground = true)
@Composable
private fun PreviewTopBarDark() {
    LaunchesTopBar(
        title = "Launches",
        backgroundColor = AppColors.Black,
        titleColor = AppColors.White
    )
}

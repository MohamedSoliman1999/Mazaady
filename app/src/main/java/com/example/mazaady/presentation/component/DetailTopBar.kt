package com.example.mazaady.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mazaady.ui.theme.AppColors

@Composable
fun DetailTopBar(
    title: String,
    backgroundColor: Color,
    contentColor: Color,
    onBackClick: () -> Unit,
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

        // Navigation bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = contentColor,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onBackClick)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = contentColor
            )
        }
    }
}

@Preview(name = "Detail Top Bar Light", showBackground = true)
@Composable
private fun PreviewDetailTopBarLight() {
    DetailTopBar(
        title = "Launch Detail (id: 110)",
        backgroundColor = AppColors.LightTopBar,
        contentColor = AppColors.Black,
        onBackClick = {}
    )
}

@Preview(name = "Detail Top Bar Dark", showBackground = true)
@Composable
private fun PreviewDetailTopBarDark() {
    DetailTopBar(
        title = "Launch Detail (id: 110)",
        backgroundColor = AppColors.DarkTopBar,
        contentColor = AppColors.White,
        onBackClick = {}
    )
}
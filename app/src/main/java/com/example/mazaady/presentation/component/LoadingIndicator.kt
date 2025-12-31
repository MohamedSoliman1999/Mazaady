package com.example.mazaady.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.mazaady.ui.theme.AppColors

@Composable
fun LoadingIndicator(
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = color)
    }
}

@Preview(name = "Loading Indicator", showBackground = true)
@Composable
private fun PreviewLoadingIndicator() {
    LoadingIndicator(color = AppColors.LightOnSurface)
}
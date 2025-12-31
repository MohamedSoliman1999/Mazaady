package com.example.mazaady.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mazaady.ui.theme.AppColors

@Composable
fun EmptyStateView(
    message: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "🔍",
                fontSize = 64.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = message,
                color = textColor,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(name = "Empty State", showBackground = true)
@Composable
private fun PreviewEmptyState() {
    EmptyStateView(
        message = "No launches found",
        textColor = AppColors.LightOnSurface
    )
}
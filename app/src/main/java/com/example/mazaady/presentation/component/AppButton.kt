package com.example.mazaady.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mazaady.ui.theme.AppColors

data class AppButtonConfig(
    val containerColor: Color = AppColors.IconCRS,
    val contentColor: Color = Color.White,
    val disabledContainerColor: Color = Color.Gray,
    val disabledContentColor: Color = Color.White
)

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    config: AppButtonConfig = AppButtonConfig(),
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = config.containerColor,
            contentColor = config.contentColor,
            disabledContainerColor = config.disabledContainerColor,
            disabledContentColor = config.disabledContentColor
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = config.contentColor,
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(name = "Button Normal", showBackground = true)
@Composable
private fun PreviewAppButton() {
    Box(modifier = Modifier.padding(16.dp)) {
        AppButton(
            text = "Book Launch",
            onClick = {}
        )
    }
}

@Preview(name = "Button Loading", showBackground = true)
@Composable
private fun PreviewAppButtonLoading() {
    Box(modifier = Modifier.padding(16.dp)) {
        AppButton(
            text = "Book Launch",
            onClick = {},
            isLoading = true
        )
    }
}

@Preview(name = "Button Disabled", showBackground = true)
@Composable
private fun PreviewAppButtonDisabled() {
    Box(modifier = Modifier.padding(16.dp)) {
        AppButton(
            text = "Book Launch",
            onClick = {},
            enabled = false
        )
    }
}
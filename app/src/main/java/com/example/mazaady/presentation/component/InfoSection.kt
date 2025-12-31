package com.example.mazaady.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mazaady.ui.theme.AppColors
@Composable
fun InfoSection(
    title: String,
    items: List<Pair<String, String>>,
    titleColor: Color,
    labelColor: Color,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = titleColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        items.forEach { (label, value) ->
            if (label.isNotEmpty()) {
                Row {
                    Text(
                        text = "$label: ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = labelColor
                    )
                    Text(
                        text = value,
                        fontSize = 14.sp,
                        color = valueColor
                    )
                }
            } else {
                Text(
                    text = value,
                    fontSize = 14.sp,
                    color = valueColor
                )
            }
        }
    }
}

@Preview(name = "Info Section Light", showBackground = true)
@Composable
private fun PreviewInfoSectionLight() {
    Box(modifier = Modifier.padding(16.dp)) {
        InfoSection(
            title = "Rocket",
            items = listOf(
                "NAME" to "Falcon 9",
                "TYPE" to "FT",
                "ID" to "falcon9"
            ),
            titleColor = AppColors.LightOnSurface,
            labelColor = AppColors.LightOnSurfaceVariant,
            valueColor = AppColors.LightOnSurface
        )
    }
}

@Preview(name = "Info Section Dark", showBackground = true, backgroundColor = 0xFF1C1C1E)
@Composable
private fun PreviewInfoSectionDark() {
    Box(modifier = Modifier.padding(16.dp)) {
        InfoSection(
            title = "Rocket",
            items = listOf(
                "NAME" to "Falcon 9",
                "TYPE" to "FT",
                "ID" to "falcon9"
            ),
            titleColor = AppColors.DarkOnSurface,
            labelColor = AppColors.DarkOnSurfaceVariant,
            valueColor = AppColors.DarkOnSurface
        )
    }
}
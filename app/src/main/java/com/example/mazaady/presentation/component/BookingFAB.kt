package com.example.mazaady.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mazaady.ui.theme.AppColors

@Composable
fun BookingFAB(
    onClick: () -> Unit,
    expanded: Boolean = true, // Can be controlled by scroll state
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        expanded = expanded,
        icon = {
            Icon(
                imageVector = Icons.Default.RocketLaunch,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        text = {
            Text(text = "Book Launch")
        },
        containerColor = AppColors.IconCRS,
        contentColor = Color.White,
        modifier = modifier
    )
}

@Preview(name = "Booking FAB Extended", showBackground = true)
@Composable
private fun PreviewBookingFABExtended() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize()
    ) {
        BookingFAB(
            onClick = {},
            expanded = true
        )
    }
}

@Preview(name = "Booking FAB Collapsed", showBackground = true)
@Composable
private fun PreviewBookingFABCollapsed() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize()
    ) {
        BookingFAB(
            onClick = {},
            expanded = false
        )
    }
}

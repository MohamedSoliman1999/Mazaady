package com.example.mazaady.presentation.component

import androidx.compose.ui.graphics.Color
import com.example.mazaady.ui.theme.AppColors
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
data class AppTextFieldConfig(
    val backgroundColor: Color = Color.White,
    val textColor: Color = Color.Black,
    val labelColor: Color = Color.Gray,
    val errorColor: Color = Color.Red,
    val focusedBorderColor: Color = AppColors.IconCRS,
    val unfocusedBorderColor: Color = Color.LightGray
)
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    config: AppTextFieldConfig = AppTextFieldConfig(),
    placeholder: String = "",
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onImeAction: () -> Unit = {}
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            isError = isError,
            enabled = enabled,
            singleLine = singleLine,
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = { onImeAction() },
                onGo = { onImeAction() },
                onSearch = { onImeAction() },
                onSend = { onImeAction() }
            ),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = config.backgroundColor,
                unfocusedContainerColor = config.backgroundColor,
                disabledContainerColor = config.backgroundColor.copy(alpha = 0.5f),
                focusedTextColor = config.textColor,
                unfocusedTextColor = config.textColor,
                focusedBorderColor = config.focusedBorderColor,
                unfocusedBorderColor = config.unfocusedBorderColor,
                focusedLabelColor = config.focusedBorderColor,
                unfocusedLabelColor = config.labelColor,
                errorBorderColor = config.errorColor,
                errorLabelColor = config.errorColor
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = config.errorColor,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Preview(name = "Text Field Light", showBackground = true)
@Composable
private fun PreviewAppTextFieldLight() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        AppTextField(
            value = "test@example.com",
            onValueChange = {},
            label = "Email",
            placeholder = "Enter your email",
            config = AppTextFieldConfig(
                backgroundColor = AppColors.LightSurface,
                textColor = AppColors.LightOnSurface,
                labelColor = AppColors.LightOnSurfaceVariant
            )
        )
    }
}

@Preview(name = "Text Field Error", showBackground = true)
@Composable
private fun PreviewAppTextFieldError() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        AppTextField(
            value = "invalid",
            onValueChange = {},
            label = "Email",
            placeholder = "Enter your email",
            isError = true,
            errorMessage = "Invalid email format",
            config = AppTextFieldConfig(
                backgroundColor = AppColors.LightSurface,
                textColor = AppColors.LightOnSurface,
                labelColor = AppColors.LightOnSurfaceVariant
            )
        )
    }
}

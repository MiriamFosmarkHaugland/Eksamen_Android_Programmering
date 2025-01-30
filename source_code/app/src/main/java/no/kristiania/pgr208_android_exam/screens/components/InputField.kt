package no.kristiania.pgr208_android_exam.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

@Composable
fun InputField(
    title: String,
    placeholder: String,
    value: String,
    onChange: (output: String) -> Unit
){
    Text(
        text = title,
        color = Color.White,
        style =
        TextStyle(
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp
        )
    )

    // Handle user input: https://developer.android.com/develop/ui/compose/text/user-input
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        label = { Text(placeholder) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color.Green,
            unfocusedBorderColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White
        ),
        onValueChange = { output -> onChange(output)}
    )
}
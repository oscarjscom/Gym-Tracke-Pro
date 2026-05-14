package com.example.gymtrackerpro.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun customTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedContainerColor = Color(0xFF1C1C1E),
    unfocusedContainerColor = Color(0xFF1C1C1E),
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = Color.DarkGray,
    cursorColor = MaterialTheme.colorScheme.primary
)

@Composable
fun LabelField(text: String) {
    Text(
        text = text,
        color = Color.LightGray,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
    )
}

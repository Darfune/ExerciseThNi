package com.example.threenitasapp.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun LoginScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(text = "UserID")
        }
    }
}
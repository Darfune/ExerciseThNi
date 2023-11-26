package com.example.threenitasapp.ui.screens.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.threenitasapp.ui.theme.onyx
import com.example.threenitasapp.ui.theme.white

@Preview
@Composable
fun TextFieldErrorDialog() {
    Dialog(onDismissRequest = { }) {
        Card(
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = onyx.copy(alpha = .9f),
                contentColor = white
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "Error")
                Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Ok")
                }
            }
        }
    }
}
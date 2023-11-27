package com.example.threenitasapp.ui.screens.login.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.threenitasapp.ui.theme.dark_jungle_green
import com.example.threenitasapp.ui.theme.onyx
import com.example.threenitasapp.ui.theme.white
import kotlinx.coroutines.Job
import kotlin.reflect.KFunction0

@Composable
fun TextFieldErrorDialog(
    errorTitle: String = "Error",
    errorBody: String = "error",
    buttonText: String,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
            dismissOnBackPress = true
        )
    ) {
        Card(
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = onyx.copy(alpha = .9f),
                contentColor = white
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = errorTitle, fontSize = 17.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = errorBody, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(21.dp))
                Box(modifier = Modifier.height(44.dp)) {
                    Button(
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = dark_jungle_green
                        )
                    ) {
                        Text(text = buttonText)
                    }
                }
            }
        }
    }
}

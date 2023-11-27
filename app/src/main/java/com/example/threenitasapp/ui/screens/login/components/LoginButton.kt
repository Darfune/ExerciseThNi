package com.example.threenitasapp.ui.screens.login.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threenitasapp.ui.screens.login.LoginViewModel
import com.example.threenitasapp.ui.screens.login.state.LoginFormEvent
import com.example.threenitasapp.ui.screens.login.state.LoginState
import com.example.threenitasapp.ui.theme.dim_gray
import com.example.threenitasapp.ui.theme.forest_green

@Composable
fun LoginButton(viewModel: LoginViewModel, state: LoginState, language: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = {
                viewModel.onValidationEvent(event = LoginFormEvent.Submit)
            },
            modifier = Modifier
                .width(188.dp)
                .height(49.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = forest_green,
                disabledContainerColor = dim_gray,
                disabledContentColor = dim_gray

            ),
            border = BorderStroke(3.dp, forest_green)
        ) {
            Text(text = state.language[language]!!.buttonText, fontSize = 17.sp)
        }
    }
}
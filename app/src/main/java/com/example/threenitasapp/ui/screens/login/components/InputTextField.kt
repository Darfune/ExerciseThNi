package com.example.threenitasapp.ui.screens.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.unit.dp
import com.example.threenitasapp.R
import com.example.threenitasapp.ui.screens.login.LoginViewModel
import com.example.threenitasapp.ui.theme.dark_jungle_green_1
import com.example.threenitasapp.ui.theme.forest_green
import com.example.threenitasapp.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(
    title: String,
    onInfoIconClicked: () -> Unit,
    userInput: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    showAndHidePassText: List<String>? = null,
    keyboardOption: KeyboardOptions,
    visualTransformation: VisualTransformation,
) {
    var passVisibility by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 36.dp, end = 36.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Row {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = white
                )
                Box(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(20.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_info_login),
                        contentDescription = "login info icon",
                        modifier = Modifier.clickable { onInfoIconClicked() }
                    )
                }
            }
            if (!showAndHidePassText.isNullOrEmpty())

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = if (passVisibility) showAndHidePassText[1]
                        else showAndHidePassText[0],
                        color = forest_green,
                        modifier = Modifier.clickable { passVisibility = !passVisibility }
                    )
                }

        }
        TextField(
            value = userInput,
            onValueChange = { onValueChange(it) },
            isError = isError,
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = white,
                focusedContainerColor = dark_jungle_green_1,
                unfocusedIndicatorColor = forest_green
            ),
            trailingIcon = {
                if (isError) {
                    R.drawable.ic_error
                }
            },
            keyboardOptions = keyboardOption,
            visualTransformation = if (!passVisibility) visualTransformation else None
        )
    }
}
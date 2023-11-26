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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.threenitasapp.R
import com.example.threenitasapp.ui.screens.login.LoginViewModel
import com.example.threenitasapp.ui.theme.dark_jungle_green_1
import com.example.threenitasapp.ui.theme.forest_green
import com.example.threenitasapp.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(
    viewModel: LoginViewModel,
    isTextFieldDialogShown: Boolean,
    typeOfField: Boolean,
    title: String,
    userInput: String,
    isError: Boolean,
    dialogText: String,
    onValueChange: (String) -> Unit,
    topPadding: Dp = 0.dp,
    passShow: Boolean = false,
    showPassText: String = "",
    keyboardOption: KeyboardOptions,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 36.dp, top = topPadding, end = 36.dp)
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
                        modifier = Modifier.clickable {
                            if (typeOfField)
                                viewModel.onUserInfoIconClicked()
                            else
                                viewModel.onPassInfoIconClicked()
                        }
                    )
                }
            }
            if (passShow)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(text = showPassText, color = forest_green)
                }
            if (isTextFieldDialogShown) {
                TextFieldInfoDialog(
                    displayText = dialogText,
                    onDismiss = {
                        if (typeOfField)
                            viewModel.onUserDismissTextFieldDialog()
                        else
                            viewModel.onPassDismissTextFieldDialog()
                    }
                )
            }
        }
        TextField(
            value = userInput,
            onValueChange = {
                onValueChange(it)
            },
            isError = isError,
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = white,
                containerColor = dark_jungle_green_1,
                unfocusedIndicatorColor = forest_green
            ),
            trailingIcon = {
                if (isError) {
                    R.drawable.ic_error
                }
            },
            keyboardOptions = keyboardOption,
            visualTransformation = if (!typeOfField)PasswordVisualTransformation() else None
        )
    }
}
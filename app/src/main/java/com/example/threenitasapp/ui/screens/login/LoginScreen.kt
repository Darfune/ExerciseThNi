package com.example.threenitasapp.ui.screens.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.threenitasapp.ui.screens.login.components.InputTextField
import com.example.threenitasapp.ui.screens.login.components.LanguageDropDown
import com.example.threenitasapp.ui.screens.login.state.LoginFormEvent
import com.example.threenitasapp.ui.screens.login.state.LoginState
import com.example.threenitasapp.ui.screens.login.components.LoginButton
import com.example.threenitasapp.ui.screens.login.components.TextFieldErrorDialog
import com.example.threenitasapp.ui.screens.login.components.TextFieldInfoDialog
import com.example.threenitasapp.ui.theme.ThreenitasAppTheme
import com.example.threenitasapp.ui.theme.onyx
import com.example.threenitasapp.ui.theme.white


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    state: LoginState,
    viewModel: LoginViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

    var error by remember {
        mutableStateOf("")
    }
    var language by rememberSaveable {
        mutableStateOf(false)
    }
    Log.d("LoginScreen: ", "$state")


    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is LoginViewModel.ValidationEvent.Success -> {
                    viewModel.getToken()
                }

                is LoginViewModel.ValidationEvent.Error -> {
                    if (viewModel.loginFormState.passwordError != null)
                        error =
                            state.language[language]!!.passError[viewModel.loginFormState.passwordError!!]
                    else error =
                        state.language[language]!!.userIdError[viewModel.loginFormState.userIdError!!]
                }
            }
        }
    }


    var dropDownShow by remember {
        mutableStateOf(false)
    }



    LoginScaffoldSetup(
        Modifier.fillMaxSize(),
        viewModel,
        state,
        dropDownShow,
        language,
        { dropDownShow = !dropDownShow },
        { language = it },
        error
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScaffoldSetup(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    state: LoginState,
    dropDownShow: Boolean,
    language: Boolean,
    omnDropDownChange: () -> Unit,
    onLanguageChange: (Boolean) -> Unit,
    error: String,
) {
    ThreenitasAppTheme {
        Surface(
            modifier = modifier,
        ) {
            Scaffold(modifier = modifier,
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = state.language[language]!!.topAppBarText,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = white
                            )
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(onyx)
                    )
                }) {
                LoginScreenContent(
                    it,
                    viewModel,
                    state,
                    dropDownShow,
                    language,
                    omnDropDownChange,
                    onLanguageChange,
                    error
                )
            }
        }
    }
}


@Composable
fun LoginScreenContent(
    paddingValues: PaddingValues,
    viewModel: LoginViewModel,
    state: LoginState,
    dropDownShow: Boolean,
    language: Boolean,
    omnDropDownChange: () -> Unit,
    onLanguageChange: (Boolean) -> Unit,
    error: String,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),

        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp)
        ) {
            InputTextField(
                onInfoIconClicked = viewModel::onUserInfoIconClicked,
                title = state.language[language]!!.userText,
                userInput = state.userId,
                isError = viewModel.loginFormState.userIdError != null,
                onValueChange = {
                    state.userId = it
                    viewModel.onValidationEvent(LoginFormEvent.UserIdChanged(it))
                },
//                onInfoIconClicked = viewModel.onUserInfoIconClicked(),
                keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Text),
                visualTransformation = None
            )
            Spacer(modifier = Modifier.height(10.dp))
            InputTextField(
                onInfoIconClicked = viewModel::onPassInfoIconClicked,
                title = state.language[language]!!.passText,
                userInput = state.password,
                isError = viewModel.loginFormState.passwordError != null,

                onValueChange = {
                    viewModel.uiState.value.password = it
                    viewModel.onValidationEvent(LoginFormEvent.PasswordChanged(it))
                },
                showAndHidePassText = listOf(
                    state.language[language]!!.showPassText,
                    state.language[language]!!.hidePassText
                ),
                keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                LanguageDropDown(state, language, dropDownShow, omnDropDownChange, onLanguageChange)
                Spacer(modifier = Modifier.height(14.dp))
                LoginButton(viewModel, state, language)
            }
            if (error.isNotBlank()) {
                TextFieldErrorDialog(error)
            }
            if (viewModel.isUserIdTextFieldDialogShown) {
                TextFieldInfoDialog(
                    displayText = state.language[language]!!.userDialogText,
                    onDismiss = viewModel::onUserDismissTextFieldDialog
                )
            }
            if (viewModel.isPassIdTextFieldDialogShown) {
                TextFieldInfoDialog(
                    displayText = state.language[language]!!.passDialogText,
                    onDismiss = viewModel::onPassDismissTextFieldDialog
                )
            }
        }
    }
}
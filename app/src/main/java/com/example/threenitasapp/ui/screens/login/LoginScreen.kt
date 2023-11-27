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
import com.example.threenitasapp.ui.screens.login.state.StateConstants
import com.example.threenitasapp.ui.theme.ThreenitasAppTheme
import com.example.threenitasapp.ui.theme.onyx
import com.example.threenitasapp.ui.theme.white


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    state: LoginState,
    viewModel: LoginViewModel = hiltViewModel(),
    onSuccess: (String) -> Unit,
) {

    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED



    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is LoginViewModel.ValidationEvent.Success -> {
                    viewModel.getToken()
                }
                is LoginViewModel.ValidationEvent.Error -> {
                    if (viewModel.loginFormState.passwordError != null) {
                        state.errorBodyToShow =
                            StateConstants.langUiText[state.currentLanguage]!!.passError[viewModel.loginFormState.passwordError!!]
                    } else state.errorBodyToShow =
                        StateConstants.langUiText[state.currentLanguage]!!.userIdError[viewModel.loginFormState.userIdError!!]
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
        state.currentLanguage,
        { dropDownShow = !dropDownShow }
    )

    if (!state.accessToken.isNullOrEmpty()){
        onSuccess(state.accessToken)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScaffoldSetup(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    state: LoginState,
    dropDownShow: Boolean,
    currentLanguage: Boolean,
    omnDropDownChange: () -> Unit
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
                                text = StateConstants.langUiText[currentLanguage]!!.topAppBarText,
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
                    currentLanguage,
                    omnDropDownChange
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
    currentLanguage: Boolean,
    omnDropDownChange: () -> Unit,
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
                title = StateConstants.langUiText[currentLanguage]!!.userText,
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
                title = StateConstants.langUiText[currentLanguage]!!.passText,
                userInput = state.password,
                isError = viewModel.loginFormState.passwordError != null,

                onValueChange = {
                    viewModel.uiState.value.password = it
                    viewModel.onValidationEvent(LoginFormEvent.PasswordChanged(it))
                },
                showAndHidePassText = listOf(
                    StateConstants.langUiText[currentLanguage]!!.showPassText,
                    StateConstants.langUiText[currentLanguage]!!.hidePassText
                ),
                keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                LanguageDropDown(
                    state,
                    currentLanguage,
                    dropDownShow,
                    omnDropDownChange,
                    viewModel::onLanguageChange
                )
                Spacer(modifier = Modifier.height(14.dp))
                LoginButton(viewModel, state, currentLanguage)
            }
            if (state.isUserIdTextFieldDialogShown) {
                Log.d("LoginScreen: ", "$state")
                TextFieldInfoDialog(
                    displayText = StateConstants.langUiText[currentLanguage]!!.userDialogText,
                    onDismiss = viewModel::onUserDismissTextFieldDialog
                )
            }
            if (state.isPassIdTextFieldDialogShown) {
                TextFieldInfoDialog(
                    displayText = StateConstants.langUiText[currentLanguage]!!.passDialogText,
                    onDismiss = viewModel::onPassDismissTextFieldDialog
                )
            }
            if (state.isErrorDialogShown) {
                TextFieldErrorDialog(
                    errorTitle = state.errorTitleToShow ?: "Not Found",
                    errorBody = state.errorBodyToShow ?: "Not Found",
                    buttonText = "return",
                    onDismiss = viewModel::onDismissErrorDialog
                )
            }

        }
    }
}
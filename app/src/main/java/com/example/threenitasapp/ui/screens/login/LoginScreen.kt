package com.example.threenitasapp.ui.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.threenitasapp.R
import com.example.threenitasapp.ui.screens.login.components.LanguageUiState
import com.example.threenitasapp.ui.theme.ThreenitasAppTheme
import com.example.threenitasapp.ui.theme.dark_jungle_green_1
import com.example.threenitasapp.ui.theme.dim_gray
import com.example.threenitasapp.ui.theme.forest_green
import com.example.threenitasapp.ui.theme.onyx
import com.example.threenitasapp.ui.theme.white


//@Preview(showSystemUi = true, showBackground = true, device = "id:pixel_3")
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {

    var userId by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }
    var dropDownShow by remember {
        mutableStateOf(false)
    }

    var language by rememberSaveable {
        mutableStateOf(false)
    }

    LoginScaffoldSetup(
        Modifier.fillMaxSize(),
        userId,
        password,
        dropDownShow,
        language,
        { userId = it },
        { password = it },
        { dropDownShow = !dropDownShow },
        { language = it }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScaffoldSetup(
    modifier: Modifier = Modifier,
    userId: String,
    password: String,
    dropDownShow: Boolean,
    language: Boolean,
    onChangeUserID: (String) -> Unit,
    onChangePass: (String) -> Unit,
    omnDropDownChange: () -> Unit,
    onLanguageChange: (Boolean) -> Unit,
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
                                text = LanguageUiState.langUiText[language]!!.topAppBarText,
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
                    userId,
                    password,
                    dropDownShow,
                    language,
                    onChangeUserID,
                    onChangePass,
                    omnDropDownChange,
                    onLanguageChange
                )
            }
        }
    }
}


@Composable
fun LoginScreenContent(
    paddingValues: PaddingValues,
    userId: String,
    password: String,
    dropDownShow: Boolean,
    language: Boolean,
    onChangeUserID: (String) -> Unit,
    onChangePass: (String) -> Unit,
    omnDropDownChange: () -> Unit,
    onLanguageChange: (Boolean) -> Unit,
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
            UserField(
                title = LanguageUiState.langUiText[language]!!.userText,
                value = userId,
                placeholder = "user id",
                onValueChange = onChangeUserID,
            )
            UserField(
                title = LanguageUiState.langUiText[language]!!.passText,
                value = password,
                placeholder = "",
                topPadding = 30.dp,
                onValueChange = onChangePass,
                passShow = true,
                showPassText = LanguageUiState.langUiText[language]!!.showPassText
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                LanguageDropDown(language, dropDownShow, omnDropDownChange, onLanguageChange)
                Spacer(modifier = Modifier.height(14.dp))
                LoginButton(language)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserField(
    title: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    topPadding: Dp = 0.dp,
    passShow: Boolean = false,
    showPassText: String = "",
) {
    var inputText by remember {
        mutableStateOf("")
    }
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

                        }
                    )
                }
            }
            if (passShow)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(text = showPassText, color = forest_green)
                }
        }
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = white,
                containerColor = dark_jungle_green_1,
                unfocusedIndicatorColor = forest_green
            ),
        )
    }
}

@Composable
fun LoginButton(language: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = { /*TODO*/ },
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
            Text(text = LanguageUiState.langUiText[language]!!.buttonText, fontSize = 17.sp)
        }
    }
}

//@Preview
@Composable
fun LanguageDropDown(
    language: Boolean,
    dropDownShow: Boolean = false,
    onDropDownChange: () -> Unit,
    onLanguageChange: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(162.dp)
            .padding(end = 32.dp),
        horizontalAlignment = Alignment.End
    ) {
        Card(
            modifier = Modifier
                .height(52.dp)
                .width(150.dp),
            shape = RoundedCornerShape(25.5.dp),
            colors = CardDefaults.cardColors(containerColor = onyx)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = LanguageUiState.langUiText[language]!!.languageIcon),
                        modifier = Modifier.size(32.dp),
                        contentDescription = null
                    )
                    Text(
                        text = LanguageUiState.langUiText[language]!!.selectedLanguage,
                        color = white,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_drop_down_menu),
                        modifier = Modifier
                            .width(15.dp)
                            .height(9.dp)
                            .clickable {
                                onDropDownChange()
                            },
                        contentDescription = null,

                        )
                }
            }
        }
        if (dropDownShow) {
            Spacer(modifier = Modifier.padding(bottom = 6.dp))
            Card(
                modifier = Modifier
                    .width(150.dp),
                shape = RoundedCornerShape(25.5.dp),
                colors = CardDefaults.cardColors(containerColor = onyx),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable { onDropDownChange() }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onLanguageChange(true)
                                onDropDownChange()
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_us_flag),
                            modifier = Modifier.size(32.dp),
                            contentDescription = null
                        )
                        Text(
                            text = "English",
                            color = white,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onLanguageChange(false)
                                onDropDownChange()
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_greek_flag),
                            modifier = Modifier.size(32.dp),
                            contentDescription = null
                        )
                        Text(
                            text = "Greek",
                            color = white,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
            }
        }
    }
}
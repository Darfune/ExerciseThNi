package com.example.threenitasapp.ui.screens.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.threenitasapp.R
import com.example.threenitasapp.ui.screens.login.state.LoginState
import com.example.threenitasapp.ui.screens.login.state.StateConstants
import com.example.threenitasapp.ui.theme.onyx
import com.example.threenitasapp.ui.theme.white

@Composable
fun LanguageDropDown(
    state: LoginState,
    language: Boolean,
    dropDownShow: Boolean = false,
    onDropDownChange: () -> Unit,
    onLanguageChange: () -> Unit,
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
                .width(150.dp)
                .clickable {
                    onDropDownChange()
                },
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
                        painter = painterResource(id = StateConstants.langUiText[language]!!.languageIcon),
                        modifier = Modifier.size(32.dp),
                        contentDescription = null
                    )
                    Text(
                        text = StateConstants.langUiText[language]!!.selectedLanguage,
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
                            .height(9.dp),
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
                                onLanguageChange()
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
                                onLanguageChange()
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
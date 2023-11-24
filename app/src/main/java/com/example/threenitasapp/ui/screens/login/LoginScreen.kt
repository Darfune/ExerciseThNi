package com.example.threenitasapp.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.threenitasapp.R
import com.example.threenitasapp.ui.theme.dark_jungle_green
import com.example.threenitasapp.ui.theme.forest_green
import com.example.threenitasapp.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = false)
@Composable
fun LoginScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(modifier = Modifier
            .fillMaxSize()
            .background(dark_jungle_green), topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(10.dp),
                title = {
                    Text(text = "Σύνδεση")
                },
            )
        }) { it ->
            LoginScreenContent(it)
        }

    }
}


@Composable
fun LoginScreenContent(paddingValues: PaddingValues) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(2f)
        ) {
            UserField()
            UserField(topPadding = 40.dp)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(188.dp)
                    .height(49.dp),
                colors = ButtonColors(
                    containerColor = white,
                    contentColor = forest_green,
                    disabledContainerColor = ,
                    disabledContentColor =

                )
            ) {
                Text(text = "Σύνδεση")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserField(topPadding: Dp = 0.dp) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 36.dp, top = topPadding, end = 36.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "UserID")
            Box(modifier = Modifier.size(20.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_login_info),
                    colorFilter = ColorFilter.tint(forest_green),
                    contentDescription = "login info icon",
                    modifier = Modifier.clickable {

                    }
                )
            }
        }
        TextField(value = "123456", onValueChange = {}, modifier = Modifier.fillMaxWidth())
    }
}

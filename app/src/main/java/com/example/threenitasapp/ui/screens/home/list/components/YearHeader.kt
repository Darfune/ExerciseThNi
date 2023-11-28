package com.example.threenitasapp.ui.screens.home.list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threenitasapp.ui.theme.dark_jungle_green


@Composable
fun YearHeader(
    year: String,
    modifier: Modifier = Modifier,
) {
    Surface (modifier = modifier, color = dark_jungle_green){

        Text(
            text = year,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 38.dp, top = 30.dp, bottom = 10.dp)
        )
    }
}
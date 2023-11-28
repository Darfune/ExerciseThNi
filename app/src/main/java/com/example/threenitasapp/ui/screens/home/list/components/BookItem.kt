package com.example.threenitasapp.ui.screens.home.list.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.threenitasapp.data.remote.models.Book
import com.example.threenitasapp.ui.theme.tufts_blue

@Composable
fun BookItem(book: Book) {


    Column {
        Box(
            modifier = Modifier
                .height(170.dp)
                .width(130.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

            AsyncImage(
                model = book.imgUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                contentAlignment = Alignment.BottomEnd,
            ) {
                LinearProgressIndicator(
                    progress = { 4f },
                    modifier = Modifier.fillMaxWidth(),
                    color = tufts_blue,
                )
            }
        }
        Text(text = book.title, color = Color.White)
    }

}
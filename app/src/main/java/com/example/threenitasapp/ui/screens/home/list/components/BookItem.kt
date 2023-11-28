package com.example.threenitasapp.ui.screens.home.list.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.threenitasapp.R
import com.example.threenitasapp.data.remote.AndroidDownloader
import com.example.threenitasapp.domain.remote.model.BookData
import com.example.threenitasapp.ui.theme.tufts_blue

@Composable
fun BookItem(book: BookData, downloader: AndroidDownloader) {

    Column() {
        Box(
            modifier = Modifier
                .width(130.dp)
                .height(170.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Box(contentAlignment = Alignment.Center) {
                AsyncImage(
                    model = book.imgUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize(),
                )
                Surface(modifier = Modifier.size(50.dp), color = Color.Transparent) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_download),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            downloader.downloadPDF(book.title.trim(), book.pdfUrl)
                            downloader.handleProgress()
                        }
                    )
                }
            }
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
        Text(
            text = book.title,
            color = Color.White,
            modifier = Modifier.width(130.dp),
            textAlign = TextAlign.Left,
            overflow = TextOverflow.Clip,
            maxLines = 1
        )
    }
}
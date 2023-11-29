package com.example.threenitasapp.ui.screens.home.list.components


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.example.threenitasapp.R
import com.example.threenitasapp.data.local.mapper.toBookEntity
import com.example.threenitasapp.domain.remote.model.BookData
import com.example.threenitasapp.ui.screens.home.list.BookListViewModel
import com.example.threenitasapp.ui.screens.home.list.state.LocalState
import com.example.threenitasapp.ui.theme.forest_green_2
import com.example.threenitasapp.ui.theme.tufts_blue
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BookItem(
    book: BookData,
    year: String,
    viewModel: BookListViewModel,
    uiLocalState: StateFlow<LocalState>,
) {

    var downloadId by rememberSaveable {
        mutableStateOf(-1L)
    }
    var progressBar = viewModel.downloader.observeDownloadProgress(downloadId).collectAsState(
        initial = 0f
    )
    var isDownloading by rememberSaveable {
        mutableStateOf(false)
    }



    Column(modifier = Modifier.padding(bottom = 20.dp)) {
        Box(
            modifier = Modifier
                .width(130.dp)
                .height(170.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            AsyncImage(
                model = book.imgUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),
            )
            if (!isDownloading && !uiLocalState.value.storedBooks!!.data!!.contains(book.toBookEntity())) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Surface(modifier = Modifier.size(50.dp), color = Color.Transparent) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_download),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                downloadId =
                                    viewModel.startDownload(title = book.title, url = book.pdfUrl)
                                isDownloading = true
                            }
                        )
                    }
                }
            } else if (progressBar.value < 100f && isDownloading && !uiLocalState.value.storedBooks!!.data!!.contains(
                    book.toBookEntity()
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    contentAlignment = Alignment.BottomEnd,
                ) {
                    LinearProgressIndicator(
                        progress = { progressBar.value },
                        modifier = Modifier.fillMaxWidth(),
                        color = tufts_blue,
                    )
                }
            } else if (progressBar.value >= 100f) {
                DownloadTriangle()
                LaunchedEffect(key1 = true) {
                    viewModel.insertBooksFromDatabaseUseCase(book.toBookEntity())
                }
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


@Preview
@Composable
fun DownloadTriangle() {
    Card(
        modifier = Modifier
            .height(40.dp)
            .width(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = forest_green_2,
            contentColor = Color.White
        ),
        shape = CutCornerShape(
            topStart = 1000.dp,
        ),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            Image(
                painter = painterResource(id = R.drawable.ic_check_w),
                contentDescription = null,
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
                    .padding(end = 2.dp)
            )
        }
    }
}
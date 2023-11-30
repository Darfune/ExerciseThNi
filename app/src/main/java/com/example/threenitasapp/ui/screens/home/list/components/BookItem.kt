package com.example.threenitasapp.ui.screens.home.list.components


import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.example.threenitasapp.R
import com.example.threenitasapp.data.local.mapper.toBookEntity
import com.example.threenitasapp.data.local.models.BookEntity
import com.example.threenitasapp.domain.remote.model.BookData
import com.example.threenitasapp.ui.screens.home.list.BookListViewModel
import com.example.threenitasapp.ui.theme.forest_green_2
import com.example.threenitasapp.ui.theme.tufts_blue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File



@Composable
fun BookItem(
    index: Int,
    book: BookData,
    date: String,
    viewModel: BookListViewModel,
    isBookInDB: Boolean,
    getAllBooksFromDB: () -> Unit,
    getDownloadId: (String, String) -> Long,
    insertBookToDB: (BookEntity) -> Unit,
) {


    var downloadId by rememberSaveable {
        mutableStateOf(-1L)
    }
    var isDownloading by rememberSaveable {
        mutableStateOf(false)
    }
    var progress by rememberSaveable {
        mutableStateOf(0f)
    }
    val context = LocalContext.current


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
            if (!isDownloading && !isBookInDB) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    IconButton(onClick = {
                        downloadId = getDownloadId(book.title, book.pdfUrl)
                        getAllBooksFromDB()
                        isDownloading = true

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_download),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                            tint = Color.White
                        )
                    }
                }
            } else if (isDownloading && !isBookInDB) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    contentAlignment = Alignment.BottomEnd,
                ) {
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth(),
                        color = tufts_blue,
                    )
                    insertBookToDB(book.toBookEntity())
                    getAllBooksFromDB()
                }
            } else if (isBookInDB && isDownloading) {
                isDownloading = false
            } else if (isBookInDB && !isDownloading) {
                DownloadTriangle(context, book)
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


@Composable
fun DownloadTriangle(context: Context, book: BookData) {
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    val f = File(context.cacheDir, book.title.trim() + ".pdf")
                    val path: Uri = FileProvider.getUriForFile(
                        context, context.applicationContext.packageName,
                        f
                    )
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(path, "application/pdf")
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    try {
                        startActivity(context, intent, null)
                    } catch (e: ActivityNotFoundException) {
                        Toast
                            .makeText(context, e.message, Toast.LENGTH_SHORT)
                            .show()
                        throw e
                    }
                }, contentAlignment = Alignment.BottomEnd
        ) {
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
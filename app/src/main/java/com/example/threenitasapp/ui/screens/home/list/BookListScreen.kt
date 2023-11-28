package com.example.threenitasapp.ui.screens.home.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.threenitasapp.data.remote.AndroidDownloader
import com.example.threenitasapp.ui.screens.home.list.components.BookItem
import com.example.threenitasapp.ui.screens.home.list.components.YearHeader
import com.example.threenitasapp.ui.screens.home.list.state.RemoteState
import ua.hospes.lazygrid.GridCells
import ua.hospes.lazygrid.LazyVerticalGrid
import ua.hospes.lazygrid.items

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun BookListScreen(
    token: String? = null,
    viewModel: BookListViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val downloader = AndroidDownloader(LocalContext.current)

    ListOfBooks(viewModel.uiRemoteState.value, downloader)
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ListOfBooks(books: RemoteState, downloader: AndroidDownloader) {

    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.SpaceBetween,
            contentPadding = PaddingValues(start = 40.dp, top = 20.dp, bottom = 30.dp),
        ) {
            books.allBooks.toSortedMap(Comparator.reverseOrder()).forEach { year, books ->
                stickyHeader {
                    YearHeader(year = year)
                }

                items(books){book ->
                    BookItem(book = book, downloader)
                }
            }
        }
    }
}

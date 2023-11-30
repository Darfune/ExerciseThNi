package com.example.threenitasapp.ui.screens.home.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.threenitasapp.data.local.mapper.toBookEntity
import com.example.threenitasapp.data.local.models.BookEntity
import com.example.threenitasapp.ui.screens.home.list.components.BookItem
import com.example.threenitasapp.ui.screens.home.list.components.DateHeader
import com.example.threenitasapp.ui.screens.home.list.state.RemoteState
import ua.hospes.lazygrid.GridCells
import ua.hospes.lazygrid.LazyVerticalGrid
import ua.hospes.lazygrid.itemsIndexed


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun BookListScreen(
    token: String? = null,
    state: RemoteState,
    viewModel: BookListViewModel = hiltViewModel(),
    navController: NavHostController,
    getDate: (String) -> String,
    getAllBooksFromDB: () -> Unit,
    getDownloadId: (String, String) -> Long,
    insertBookToDB: (BookEntity) -> Unit,
) {
    LaunchedEffect(key1 = true ){
        viewModel.getAllData(token ?: "")
    }

    ListOfBooks(
        state,
        viewModel,
        getDate,
        getAllBooksFromDB,
        getDownloadId,
        insertBookToDB
    )
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ListOfBooks(
    state: RemoteState,
    viewModel: BookListViewModel,
    getDate: (String) -> String,
    getAllBooksFromDB: () -> Unit,
    getDownloadId: (String, String) -> Long,
    insertBookToDB: (BookEntity) -> Unit,
) {
    Column {
        if ((state.isLoadingDB && state.isLoadingApi) || state.booksFromApi.isNullOrEmpty()) {
            CircularProgressIndicator()
        } else if (state.error.isNotBlank()) {
            Text(text = state.error)
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.SpaceBetween,
                contentPadding = PaddingValues(start = 40.dp, top = 20.dp, bottom = 30.dp),
            ) {
                state.booksFromApi!!.toSortedMap(Comparator.reverseOrder())
                    .forEach { (date, books) ->
                        stickyHeader {
                            DateHeader(date = getDate(date))
                        }
                        itemsIndexed(books) { index, book ->
                            BookItem(
                                index,
                                book,
                                date,
                                viewModel,
                                state.booksFromDB!!.contains(book.toBookEntity()),
                                getAllBooksFromDB,
                                getDownloadId,
                                insertBookToDB
                            )
                        }
                    }
            }
        }
    }
}

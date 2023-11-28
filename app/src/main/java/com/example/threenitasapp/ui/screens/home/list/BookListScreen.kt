package com.example.threenitasapp.ui.screens.home.list

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.threenitasapp.ui.screens.home.list.components.BookItem
import com.example.threenitasapp.ui.screens.home.list.state.RemoteState

@Composable
fun BookListScreen(
    token: String? = null,
    viewModel: BookListViewModel = hiltViewModel(),
    navController: NavHostController,
) {

    ListOfBooks(viewModel.uiRemoteState.value)
}

@Composable
fun ListOfBooks(books: RemoteState) {
//    if (books.isLoading) {
//        CircularProgressIndicator()
//    } else if (books.error.isNotBlank()) {
//        Text(text = "An Error occurred", color = MaterialTheme.colorScheme.error)
//    } else {
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(4.dp)){
            items(items = books.allBooks){ book ->
                Log.d("BookListScreen", "ListOfBooks: $book")
                BookItem(book)
            }
//        }
    }
}

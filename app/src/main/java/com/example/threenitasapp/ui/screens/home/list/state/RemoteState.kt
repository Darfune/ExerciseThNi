package com.example.threenitasapp.ui.screens.home.list.state

import com.example.threenitasapp.data.local.models.BookEntity
import com.example.threenitasapp.data.remote.models.Book
import com.example.threenitasapp.domain.remote.model.BookData

data class RemoteState (
    var booksFromApi: Map<String, List<BookData>>? = null,
    var booksFromDB: List<BookEntity>? = null,
    val isLoadingDB: Boolean = false,
    val isLoadingApi: Boolean = false,
    var error: String = ""
)
package com.example.threenitasapp.ui.screens.home.list.state

import com.example.threenitasapp.data.remote.models.Book
import com.example.threenitasapp.domain.remote.model.BookData

data class RemoteState (
    val allBooks: HashMap<String, List<BookData>> = hashMapOf(),
    val isLoading: Boolean = false,
    val error: String = ""
)
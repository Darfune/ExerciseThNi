package com.example.threenitasapp.ui.screens.home.list.state

import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.data.remote.models.Book

data class RemoteState (
    val allBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
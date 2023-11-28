package com.example.threenitasapp.ui.screens.home.list.state

import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.data.local.models.BookEntity
import com.example.threenitasapp.data.remote.models.Book

data class LocalState(
    val storedBooks: Resource<List<BookEntity>>? = null
)

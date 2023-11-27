package com.example.threenitasapp.ui.screens.list.state

import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.data.local.models.Book

data class LocalState(
    val storedBooks: Resource<List<Book>>? = null
)

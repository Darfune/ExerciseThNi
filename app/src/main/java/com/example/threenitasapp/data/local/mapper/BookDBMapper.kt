package com.example.threenitasapp.data.local.mapper

import com.example.threenitasapp.data.local.models.BookEntity
import com.example.threenitasapp.domain.local.model.BookDomain
import com.example.threenitasapp.domain.remote.model.BookData

fun BookDomain.toBookEntity(): BookEntity =
    BookEntity(id = id)

fun BookData.toBookEntity(): BookEntity =
    BookEntity(id = id)
package com.example.threenitasapp.data.local.mapper

import com.example.threenitasapp.data.local.models.BookEntity
import com.example.threenitasapp.domain.local.model.BookDomain

fun BookEntity.toBookDomain(): BookDomain =
    BookDomain(
        id = id,
        title = title,
        imgUrl = imgUrl,
        dateReleased = dateReleased,
        pdfUrl = pdfUrl
    )

fun BookDomain.toBookEntity(): BookEntity =
    BookEntity(
        id = id,
        title = title,
        imgUrl = imgUrl,
        dateReleased = dateReleased,
        pdfUrl = pdfUrl
    )
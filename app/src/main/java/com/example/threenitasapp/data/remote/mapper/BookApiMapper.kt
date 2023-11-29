package com.example.threenitasapp.data.remote.mapper

import com.example.threenitasapp.data.remote.models.Book
import com.example.threenitasapp.domain.remote.model.BookData

fun Book.toBookData(): BookData =
    BookData (
        id = id,
        title = title,
        imgUrl = imgUrl,
        pdfUrl = pdfUrl,
    )
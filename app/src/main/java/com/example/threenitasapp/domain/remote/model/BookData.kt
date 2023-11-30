package com.example.threenitasapp.domain.remote.model



data class BookData(
    val id: Int,
    val title: String,
    val imgUrl: String,
    val pdfUrl: String,
    var progress: Float = 0f,
    var isDownloading: Boolean = false,
    var isDownloaded: Boolean = false
)

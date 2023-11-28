package com.example.threenitasapp.domain.local.model

data class BookDomain (
    val id: Int,
    val title: String,
    val imgUrl: String,
    val dateReleased: String,
    val pdfUrl: String,
)
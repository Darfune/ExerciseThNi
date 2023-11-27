package com.example.threenitasapp.data.remote.models


import com.google.gson.annotations.SerializedName

data class Book(
    val id: Int,
    val title: String,
    @SerializedName("img_url")
    val imgUrl: String,
    @SerializedName("date_released")
    val dateReleased: String,
    @SerializedName("pdf_url")
    val pdfUrl: String,
)
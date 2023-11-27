package com.example.threenitasapp.data.remote.models.usertoken


import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("date_released")
    val dateReleased: String,
    val id: Int,
    @SerializedName("img_url")
    val imgUrl: String,
    @SerializedName("pdf_url")
    val pdfUrl: String,
    val title: String
)
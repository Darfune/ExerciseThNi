package com.example.threenitasapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    val id: Int,
    val title: String,
    val imgUrl: String,
    val dateReleased: String,
    val pdfUrl: String,
)
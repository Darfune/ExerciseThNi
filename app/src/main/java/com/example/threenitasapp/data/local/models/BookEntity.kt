package com.example.threenitasapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books_table")
data class BookEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
)
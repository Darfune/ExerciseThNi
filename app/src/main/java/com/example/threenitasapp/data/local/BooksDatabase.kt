package com.example.threenitasapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.threenitasapp.data.local.models.BookEntity


@Database(
    entities = [BookEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BooksDatabase: RoomDatabase() {
    abstract val bookDao: BookDao
}
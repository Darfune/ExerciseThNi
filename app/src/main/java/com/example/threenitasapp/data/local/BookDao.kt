package com.example.threenitasapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.threenitasapp.data.local.models.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books_table")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books_table WHERE id=:id")
    fun getBookById(id: Int): Flow<BookEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(bookEntity: BookEntity)

    @Query("DELETE FROM books_table WHERE id=:id")
    suspend fun delete(id: Int)
}
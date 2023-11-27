package com.example.threenitasapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.threenitasapp.data.local.models.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY id")
    fun getAllBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE id=:id ORDER BY dateReleased")
    fun getBookById(id: Int):Flow<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBook(book: Book)

    @Query("DELETE FROM books WHERE id=:id")
    suspend fun delete(id: Int)
}
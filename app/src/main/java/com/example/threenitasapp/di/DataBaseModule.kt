package com.example.threenitasapp.di

import android.content.Context
import androidx.room.Room
import com.example.threenitasapp.data.local.BookDao
import com.example.threenitasapp.data.local.BooksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDao(database: BooksDatabase): BookDao = database.bookDao

    @Provides
    @Singleton
    fun provideBookDatabase(@ApplicationContext context: Context): BooksDatabase =
        Room.databaseBuilder(
            context = context,
            klass = BooksDatabase::class.java,
            name = "books_db"
        ).build()
}
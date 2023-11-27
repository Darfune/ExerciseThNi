package com.example.threenitasapp.di

import com.example.threenitasapp.data.local.repository.BooksDatabaseRepositoryImpl
import com.example.threenitasapp.data.remote.repository.BooksRemoteRepositoryImpl
import com.example.threenitasapp.domain.repository.local.BooksDatabaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDatabaseRepository(repositoryImpl: BooksDatabaseRepositoryImpl): BooksDatabaseRepository
}
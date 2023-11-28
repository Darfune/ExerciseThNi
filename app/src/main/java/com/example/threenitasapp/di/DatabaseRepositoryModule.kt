package com.example.threenitasapp.di

import com.example.threenitasapp.data.local.repository.BookDatabaseRepositoryImpl
import com.example.threenitasapp.domain.local.repository.BookDatabaseRepository
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
    abstract fun bindRepository(repositoryImpl: BookDatabaseRepositoryImpl): BookDatabaseRepository
}
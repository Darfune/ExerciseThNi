package com.example.threenitasapp.di

import com.example.threenitasapp.data.remote.BooksRemoteApi
import com.example.threenitasapp.data.remote.repository.BooksRemoteRepositoryImpl
import com.example.threenitasapp.domain.repository.local.BooksDatabaseRepository
import com.example.threenitasapp.domain.repository.remote.BooksRemoteRepository
import com.example.threenitasapp.domain.usecases.client.ValidationResult
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBooksRemoteRepository(api: BooksRemoteApi): BooksRemoteRepository =
        BooksRemoteRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideValidationResult(): ValidationResult = ValidationResult(false, null)
}
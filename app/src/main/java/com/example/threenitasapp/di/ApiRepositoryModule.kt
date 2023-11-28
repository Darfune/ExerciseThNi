package com.example.threenitasapp.di

import com.example.threenitasapp.data.remote.BooksRemoteApi
import com.example.threenitasapp.data.remote.repository.BooksRemoteRepositoryImpl
import com.example.threenitasapp.domain.remote.repository.BooksRemoteRepository
import com.example.threenitasapp.domain.client.usecase.ValidationResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiRepositoryModule {

    @Provides
    @Singleton
    fun provideBooksRemoteRepository(api: BooksRemoteApi): BooksRemoteRepository =
        BooksRemoteRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideValidationResult(): ValidationResult = ValidationResult(false, null)
}
package com.example.threenitasapp.di

import com.example.threenitasapp.common.Constants
import com.example.threenitasapp.data.remote.BooksRemoteApi
import com.example.threenitasapp.data.remote.repository.BooksRemoteRepositoryImpl
import com.example.threenitasapp.domain.repository.BooksRemoteRepository
import com.example.threenitasapp.domain.usecases.client.ValidationResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesBooksRemoteApi(): BooksRemoteApi = Retrofit
        .Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BooksRemoteApi::class.java)

    @Provides
    @Singleton
    fun provideBooksRemoteRepository(api: BooksRemoteApi): BooksRemoteRepository = BooksRemoteRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideValidationResult(): ValidationResult = ValidationResult(false, null)
}
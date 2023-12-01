package com.example.threenitasapp.di

import android.content.Context
import com.example.threenitasapp.data.remote.downloader.AndroidDownloader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DownloaderModule {
    @Provides
    @Singleton
    fun provideDownloader(@ApplicationContext context: Context): AndroidDownloader =
        AndroidDownloader(context)

}
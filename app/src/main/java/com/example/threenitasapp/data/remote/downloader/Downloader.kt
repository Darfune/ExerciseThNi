package com.example.threenitasapp.data.remote.downloader

interface Downloader {
    fun downloadPDF(title: String, url: String): Long
}
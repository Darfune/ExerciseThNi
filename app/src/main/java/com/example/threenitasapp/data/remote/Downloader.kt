package com.example.threenitasapp.data.remote

interface Downloader {
    fun downloadPDF(title: String, url: String): Long
}
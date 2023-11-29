package com.example.threenitasapp.data.remote

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.DownloadManager.Request
import android.content.Context
import android.database.ContentObserver
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log
import kotlin.math.sign

class AndroidDownloader @Inject constructor(private val context: Context) : Downloader {

    private val downloadManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        context.getSystemService(DownloadManager::class.java)
    } else {
        TODO("VERSION.SDK_INT < M")
    }
//    private val _percentage = MutableStateFlow(0L)
//    val percentage: StateFlow<Long> = _percentage.asStateFlow()


    override fun downloadPDF(title: String, url: String): Long =
        downloadManager.enqueue(
            DownloadManager.Request(url.toUri())
                .setMimeType("application/pdf")
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle("$title.pdf")
                .addRequestHeader("Authorization", "Bearer <toke>")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$title.pdf")
        )


    fun observeDownloadProgress(id: Long): Flow<Float> {
        return flow {
            var isFinishedDl = false
            var progress = 0L
            while (!isFinishedDl) {
                val q = DownloadManager.Query().setFilterById(id)
                val cursor = downloadManager.query(q)
                if (cursor.moveToFirst()) {
                    val colStatus = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    if (colStatus >= 0) {
                        when (cursor.getInt(colStatus)) {
                            DownloadManager.STATUS_FAILED -> {
                                isFinishedDl = true
                            }

                            DownloadManager.STATUS_PAUSED -> {}
                            DownloadManager.STATUS_PENDING -> {}
                            DownloadManager.STATUS_RUNNING -> {
                                println("statusRunning - ")
                                val colIdTotal =
                                    cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                                println("colId Total: ${cursor.getLong(colIdTotal)}")
                                if (colIdTotal >= 0) {
                                    val total = cursor.getLong(colIdTotal)
                                    if (total > 0L) {
                                        val colIdBytes =
                                            cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                                        println("colIdBytes Total: ${cursor.getLong(colIdBytes)}")
                                        if (colIdBytes >= 0) {
                                            val downloaded = cursor.getLong(colIdBytes)
                                            println("Progress ${(downloaded * 100 / total)}")
                                            progress = ((downloaded * 100 / total))
//                                            println("dl progress... $progress%")
                                            emit(progress.toFloat())
                                        }
                                    }
                                }
                            }

                            DownloadManager.STATUS_SUCCESSFUL -> {

                                println("finished - dl progress: $progress%")
                                emit(100f)
                                isFinishedDl = true
                            }
                        }
                    }
                } else {
                    isFinishedDl = true
                }
                cursor.close() // not sure about this line
                delay(50L)
            }
            emit(100f)
            println("end handleProgress.")
        }

    }
}
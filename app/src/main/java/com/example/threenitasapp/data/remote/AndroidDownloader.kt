package com.example.threenitasapp.data.remote

import android.app.DownloadManager
import android.app.DownloadManager.Request
import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log

class AndroidDownloader(private val context: Context) : Downloader {

    var downloadId by mutableStateOf(0L)
    var isFinishedDl by mutableStateOf(true)
    var progress by mutableStateOf(0)
    private val downloadManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        context.getSystemService(DownloadManager::class.java)
    } else {
        TODO("VERSION.SDK_INT < M")
    }

    override fun downloadPDF(title: String, url: String): Long =
        downloadManager.enqueue(
            DownloadManager.Request(url.toUri())
                .setMimeType("application/pdf")
                .setAllowedNetworkTypes(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle("$title.pdf")
                .addRequestHeader("Authorization", "Bearer <toke>")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$title.pdf")
        )

    fun handleProgress() {
        Log.d("HandleProgress: ","start handleProgress...")
        isFinishedDl = false

        CoroutineScope(Dispatchers.Default).launch {
            while (!isFinishedDl) {
                val q = DownloadManager.Query().setFilterById(downloadId)
                val cursor = downloadManager.query(q)
                if (cursor.moveToFirst()) {
                    val colStatus = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    if (colStatus >= 0) {
                        when (cursor.getInt(colStatus)) {
                            DownloadManager.STATUS_FAILED -> {
                                Log.d("HandleProgress", "Failed ")
                                isFinishedDl = true
                            }

                            DownloadManager.STATUS_PAUSED -> {}
                            DownloadManager.STATUS_PENDING -> {}
                            DownloadManager.STATUS_RUNNING -> {
                                Log.d("HandleProgress: ","statusRunning - ")
                                val colIdTotal =
                                    cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                                if (colIdTotal >= 0) {
                                    val total = cursor.getLong(colIdTotal)
                                    if (total > 0L) {
                                        val colIdBytes =
                                            cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                                        if (colIdBytes >= 0) {
                                            val downloaded = cursor.getLong(colIdBytes)
                                            progress = (downloaded * 100L / total).toInt()
                                            Log.d("HandleProgress:"," dl progress... $progress%")
                                        }
                                    }
                                }
                            }

                            DownloadManager.STATUS_SUCCESSFUL -> {
                                progress = 100
                                Log.d("HandleProgress: ","finished - dl progress: $progress%")
                                isFinishedDl = true
                            }
                        }
                    }
                } else {
                    isFinishedDl = true
                }
//                cursor.close() // not sure about this line
                delay(5L) // delay to avoid dozens of line printing
            }
            Log.d("HandleProgress: ","end handleProgress.")
        }
    }
}
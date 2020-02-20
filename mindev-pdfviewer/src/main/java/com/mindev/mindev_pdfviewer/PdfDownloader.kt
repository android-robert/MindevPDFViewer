package com.mindev.mindev_pdfviewer

import android.annotation.TargetApi
import android.os.Build
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.File
import java.net.URL


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class PdfDownloader(
    private val file: File,
    private val downLoadUrl: String,
    private val statusListener: MindevPDFViewer.MindevViewerStatusListener
) : CoroutineScope by PdfScope() {

    init {
        download()
    }

    private fun download() = launch {
        withContext(Dispatchers.IO) {
            statusListener.onStartDownload()
            if (file.exists()) file.delete()
            try {
                val bufferSize = 8192
                val url = URL(downLoadUrl)

                val connection = url.openConnection().also { it.connect() }

                val totalLength = connection.contentLength
                val inputStream = BufferedInputStream(url.openStream(), bufferSize)
                val outputStream = file.outputStream()
                var downloaded = 0

                do {
                    val data = ByteArray(bufferSize)
                    val count = inputStream.read(data)
                    if (count == -1) break
                    if (totalLength > 0) {
                        downloaded += bufferSize
                        withContext(Dispatchers.Main) {
                            (downloaded.toFloat() / totalLength.toFloat() * 100F)
                                .toInt()
                                .let(statusListener::onProgressDownload)
                        }
                    }
                    outputStream.write(data, 0, count)
                } while (true)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    statusListener.onFail(e)
                }
                cancel()
            }
        }
        statusListener.onSuccessDownLoad(file.absolutePath)
    }
}
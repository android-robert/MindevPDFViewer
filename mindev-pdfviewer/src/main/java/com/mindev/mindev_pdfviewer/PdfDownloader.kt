package com.mindev.mindev_pdfviewer

import android.annotation.TargetApi
import android.os.Build
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.File
import java.io.IOException
import java.net.URL


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class PdfDownloader(
    private val file: File,
    private val downLoadUrl: String,
    private val statusListener: MindevPDFViewer.MindevViewerStatusListener
) : CoroutineScope by PdfScope() {

    companion object {
        const val BUFFER_SIZE = 8192
    }

    init {
        download()
    }

    private fun download() = launch {
        statusListener.onStartDownload()
        withContext(Dispatchers.IO) {
            try {
                if (file.exists()) file.delete()
                val bufferSize = BUFFER_SIZE
                val url = URL(downLoadUrl)

                val connection = url.openConnection().also { it.connect() }
                val totalLength = connection.contentLength
                var downloaded = 0

                BufferedInputStream(url.openStream(), bufferSize).use { input ->
                    file.outputStream().use { output ->
                        do {
                            val data = ByteArray(bufferSize)
                            val count = input.read(data)
                            if (count == -1) break
                            if (totalLength > 0) {
                                downloaded += bufferSize
                                withContext(Dispatchers.Main) {
                                    (downloaded.toFloat() / totalLength.toFloat() * 100F)
                                        .toInt()
                                        .let(statusListener::onProgressDownload)
                                }
                            }
                            output.write(data, 0, count)
                        } while (true)

                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    statusListener.onFail(e)
                }
                cancel()
            }
        }
        statusListener.onSuccessDownLoad(file.absolutePath)
    }
}
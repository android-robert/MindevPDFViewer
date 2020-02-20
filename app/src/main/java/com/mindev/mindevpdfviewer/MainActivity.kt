package com.mindev.mindevpdfviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mindev.mindev_pdfviewer.MindevPDFViewer
import com.mindev.mindev_pdfviewer.PdfScope
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://gahp.net/wp-content/uploads/2017/09/sample.pdf"
        pdf.initializePDFDownloader(url, statusListener)
        lifecycle.addObserver(PdfScope())
    }

    private val statusListener = object : MindevPDFViewer.MindevViewerStatusListener {
        override fun onStartDownload() {
            tv.text = "start"
        }

        override fun onPageChanged(position: Int, total: Int) {
            tv.text = "${position + 1} / $total"
        }

        override fun onProgressDownload(currentStatus: Int) {
            tv.text = "$currentStatus %"
        }

        override fun onSuccessDownLoad(path: String) {
            pdf.fileInit(path)
        }

        override fun onFail(error: Throwable) {
        }

        override fun unsupportedDevice() {
        }

    }
}

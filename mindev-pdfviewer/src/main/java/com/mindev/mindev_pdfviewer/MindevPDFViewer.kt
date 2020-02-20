package com.mindev.mindev_pdfviewer

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.pdf_viewer_view.view.*
import java.io.File

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class MindevPDFViewer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    interface MindevViewerStatusListener {
        fun onStartDownload()
        fun onPageChanged(position: Int, total: Int)
        fun onProgressDownload(currentStatus: Int)
        fun onSuccessDownLoad(path: String)
        fun onFail(error: Throwable)
        fun unsupportedDevice()
    }

    private lateinit var pdfRendererCore: PDFCore
    private lateinit var statusListener: MindevViewerStatusListener
    private val pageTotalCount get() = pdfRendererCore.getPDFPagePage()

    fun initializePDFDownloader(url: String, statusListener: MindevViewerStatusListener) {
        this.statusListener = statusListener
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // 미지원 디바이스
            statusListener.unsupportedDevice()
            return
        }
        val cacheFile = File(context.cacheDir, "mindevPDF.pdf")
        PdfDownloader(cacheFile, url, statusListener)
    }

    fun fileInit(path: String) {
        Log.e("path",path)
        pdfRendererCore = PDFCore(context, File(path))

        LayoutInflater.from(context)
            .inflate(R.layout.pdf_viewer_view, this, false)
            .let(::addView)

        val rv = recyclerView.apply {
            adapter = PDFAdapter(pdfRendererCore)
            addOnScrollListener(scrollListener)
        }

        rv.let(PagerSnapHelper()::attachToRecyclerView)
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            (recyclerView.layoutManager as LinearLayoutManager).run {
                var foundPosition = findFirstCompletelyVisibleItemPosition()
                if (foundPosition != RecyclerView.NO_POSITION) {
                    statusListener.onPageChanged(foundPosition, pageTotalCount)
                    return@run
                }
                foundPosition = findFirstVisibleItemPosition()
                if (foundPosition != RecyclerView.NO_POSITION) {
                    statusListener.onPageChanged(foundPosition, pageTotalCount)
                    return@run
                }
            }
        }
    }
}
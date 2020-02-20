package com.mindev.mindev_pdfviewer

import android.graphics.Bitmap
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.davemorrissey.labs.subscaleview.ImageSource
import kotlinx.android.synthetic.main.pdf_item.view.*

class PDFViewHolder(itemView: View, private val core: PDFCore) :
    RecyclerView.ViewHolder(itemView) {

    fun bind() = with(itemView) {
        img.recycle()
        core.renderPage(adapterPosition) { bitmap: Bitmap?, currentPage: Int ->
            if (currentPage != adapterPosition) return@renderPage
            bitmap?.let(ImageSource::bitmap)?.let(img::setImage)
        }
    }
}
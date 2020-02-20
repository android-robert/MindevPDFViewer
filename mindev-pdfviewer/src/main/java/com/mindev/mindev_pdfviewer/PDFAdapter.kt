package com.mindev.mindev_pdfviewer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PDFAdapter(private val core: PDFCore) : RecyclerView.Adapter<PDFViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PDFViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.pdf_item, parent, false)
            .let { PDFViewHolder(it, core) }
    }

    override fun getItemCount(): Int = core.getPDFPagePage()
    override fun onBindViewHolder(holder: PDFViewHolder, position: Int) = holder.bind()
}
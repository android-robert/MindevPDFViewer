package com.mindev.mindev_pdfviewer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PdfAdapter(private val core: PdfCore,private  val isAnimation:Boolean) : RecyclerView.Adapter<PdfViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.pdf_item, parent, false)
            .let { PdfViewHolder(it, core) }
    }

    override fun getItemCount(): Int = core.getPDFPagePage()
    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) = holder.bind(isAnimation)
}
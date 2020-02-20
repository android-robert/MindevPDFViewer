package com.mindev.mindev_pdfviewer

class PDFAdapter(private val core: PDFCore) : _root_ide_package_.androidx.recyclerview.widget.RecyclerView.Adapter<PDFViewHolder>() {
    override fun onCreateViewHolder(parent: _root_ide_package_.android.view.ViewGroup, viewType: Int): PDFViewHolder {
        return _root_ide_package_.android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.pdf_item, parent, false)
            .let { PDFViewHolder(it, core) }
    }

    override fun getItemCount(): Int = core.getPDFPagePage()
    override fun onBindViewHolder(holder: PDFViewHolder, position: Int) = holder.bind()
}
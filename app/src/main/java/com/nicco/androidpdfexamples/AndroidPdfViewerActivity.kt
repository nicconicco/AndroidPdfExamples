package com.nicco.androidpdfexamples

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_android_pdf_viewer.*


class AndroidPdfViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_pdf_viewer)

        pdfViewer.fromAsset("extend_pdf.pdf").load()

        btnPageLeft.setOnClickListener {
            pdfViewer.jumpTo(pdfViewer.currentPage - 1)
        }

        btnPageRight.setOnClickListener {
            pdfViewer.jumpTo(pdfViewer.currentPage + 1)
        }

        btnShare.setOnClickListener {
            val thing =
                Uri.parse("android.resource://com.nicco.androidpdfexamples/assets/extend_pdf.pdf")

            val intent = Intent(Intent.ACTION_SEND)
            intent.setDataAndType(thing, "application/pdf")
        }
    }
}

package com.nicco.androidpdfexamples

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.content.FileProvider.getUriForFile
import kotlinx.android.synthetic.main.activity_android_pdf_viewer.*
import java.io.File
import java.security.AccessController.getContext


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
            val imagePath = File(filesDir, "assets")
            val newFile = File(imagePath, "extend_pdf.pdf")
            val contentUri =
                getUriForFile(this, "com.nicco.androidpdfexamples", newFile)

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                putExtra(Intent.EXTRA_STREAM, contentUri)
                type = "application/pdf"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}

//fun getByImage(context: Context, file: File) {
//    FileProvider.getUriForFile(
//        context,
//        SyncStateContract.Constants.PACKAGE_FILE_PROVIDER,
//        file
//    )?.let {
//        with(Intent()) {
//            action = Intent.ACTION_SEND
//            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            setDataAndType(it, context.contentResolver.getType(it))
//            type = context.contentResolver?.getType(it)
//            putExtra(Intent.EXTRA_STREAM, it)
//            context.startActivity(Intent.createChooser(this, null))
//        }
//    }
//}
//}

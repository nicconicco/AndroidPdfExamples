package com.nicco.androidpdfexamples

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider.getUriForFile
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.nicco.androidpdfexamples.util.PDF_64
import com.nicco.androidpdfexamples.util.PDF_64.storetoPdfandOpen
import kotlinx.android.synthetic.main.activity_android_pdf_viewer.*
import java.io.File


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

            Dexter.withContext(this)
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        sharePdf()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest?>?,
                        token: PermissionToken?
                    ) {

                    }
                }).check()
        }
    }

    private fun sharePdf() {

        storetoPdfandOpen(this, PDF_64.pdf)
//        val imagePath = File(filesDir, "assets")
//        val newFile = File(imagePath, "extend_pdf.pdf")
//        val contentUri =
//            getUriForFile(this, "com.nicco.androidpdfexamples", newFile)
//
//        val sendIntent: Intent = Intent().apply {
//            action = Intent.ACTION_SEND
//            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            putExtra(Intent.EXTRA_STREAM, contentUri)
//            type = "application/pdf"
//        }
//
//        val shareIntent = Intent.createChooser(sendIntent, null)
//        startActivity(shareIntent)
    }

    private fun codigo(context: Context, file: File) {
        getUriForFile(
            context,
            "com.nicco.androidpdfexamples",
            file
        )?.let {
            with(Intent()) {
                action = Intent.ACTION_SEND
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                setDataAndType(it, context.contentResolver.getType(it))
                type = context.contentResolver?.getType(it)
                putExtra(Intent.EXTRA_STREAM, it)
                context.startActivity(Intent.createChooser(this, null))
            }
        }
    }
}

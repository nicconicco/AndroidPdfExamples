package com.nicco.androidpdfexamples

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.nicco.androidpdfexamples.util.PDF_64
import com.nicco.androidpdfexamples.util.PDF_64.storetoPdfandShare
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
        storetoPdfandShare(this, PDF_64.pdf)
    }
}

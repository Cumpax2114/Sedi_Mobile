package dev.franklinbg.sedimobile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.franklinbg.sedimobile.databinding.ActivityVisorPdfBinding

class VisorPDFActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVisorPdfBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisorPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pdf = intent.getByteArrayExtra("pdf")
        binding.pdfView.fromBytes(pdf).load()
    }
}
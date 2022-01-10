package dev.franklinbg.sedimobile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.franklinbg.sedimobile.databinding.ActivityReportesBinding

class ReportesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
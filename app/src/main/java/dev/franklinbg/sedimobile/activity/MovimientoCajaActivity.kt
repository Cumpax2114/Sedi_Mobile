package dev.franklinbg.sedimobile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.franklinbg.sedimobile.databinding.ActivityMovimientoCajaBinding
import dev.franklinbg.sedimobile.dialog.SeleccionePersonaDialog

class MovimientoCajaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovimientoCajaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovimientoCajaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            btnSelectPerson.setOnClickListener {
                SeleccionePersonaDialog().show(supportFragmentManager, "")
            }
        }
    }
}
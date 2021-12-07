package dev.franklinbg.sedimobile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.preference.PreferenceManager
import dev.franklinbg.sedimobile.databinding.ActivityCajaBinding
import kotlin.properties.Delegates

class CajaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCajaBinding
    private var monto by Delegates.notNull<Float>()
    private var isOpen by Delegates.notNull<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCajaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        loadData()
    }

    private fun loadData() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        monto = sp.getFloat("monto", 0.0f)
        isOpen = sp.getBoolean("isOpen", false)
        with(binding) {
            edtMonto.setText(monto.toString())
            if (isOpen) {
                btnChangeCajaStatus.text = "Cerrar Caja"
            } else {
                btnChangeCajaStatus.text = "Abrir caja"
            }
        }

    }

    private fun initListeners() {
        with(binding) {
            edtMonto.addTextChangedListener {
                tiMonto.isErrorEnabled = false
            }
            btnChangeCajaStatus.setOnClickListener {
                if (validate()) {
                    saveCajaStatus()
                }
            }
        }
    }

    private fun saveCajaStatus() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sp.edit()
        editor.putFloat("monto", binding.edtMonto.text!!.toString().toFloat())
        isOpen = !isOpen
        editor.putBoolean("isOpen", isOpen)
        editor.apply()
        Toast.makeText(
            this,
            "Caja ${if (isOpen) "abierta" else "cerrada"} correctamente",
            Toast.LENGTH_SHORT
        ).show()
        this.finish()

    }

    private fun validate(): Boolean {
        var valid = true
        with(binding) {
            if (edtMonto.text!!.isEmpty()) {
                valid = false
                tiMonto.error = "ingresa un monto"
            }
        }
        return valid
    }
}
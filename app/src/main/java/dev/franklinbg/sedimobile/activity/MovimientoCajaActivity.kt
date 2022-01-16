package dev.franklinbg.sedimobile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dev.franklinbg.sedimobile.databinding.ActivityMovimientoCajaBinding
import dev.franklinbg.sedimobile.dialog.SeleccionePersonaDialog
import dev.franklinbg.sedimobile.model.ConceptoMovCaja
import dev.franklinbg.sedimobile.model.MetodoPago
import dev.franklinbg.sedimobile.utils.PersonMovCajaContainer
import dev.franklinbg.sedimobile.viewmodel.ConceptoMovCajaViewModel
import dev.franklinbg.sedimobile.viewmodel.MetodoPagoViewModel

class MovimientoCajaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovimientoCajaBinding
    private lateinit var conceptoMovCajaViewModel: ConceptoMovCajaViewModel
    private lateinit var metodoPagoViewModel: MetodoPagoViewModel
    val conceptosMC = ArrayList<ConceptoMovCaja>()
    val metodosPago = ArrayList<MetodoPago>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovimientoCajaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModels()
        loadData()
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            btnSelectPerson.setOnClickListener {
                SeleccionePersonaDialog().show(supportFragmentManager, "")
            }
        }
    }

    private fun initViewModels() {
        val vmp = ViewModelProvider(this)
        conceptoMovCajaViewModel = vmp[ConceptoMovCajaViewModel::class.java]
        metodoPagoViewModel = vmp[MetodoPagoViewModel::class.java]
    }

    private fun loadData() {
        conceptoMovCajaViewModel.listActivos().observe(this) {
            if (it.rpta == 1) {
                with(conceptosMC) {
                    conceptosMC.clear()
                    conceptosMC.addAll(it.body!!)
                }
                binding.cboConceptoMovCaja.setAdapter(
                    ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        conceptosMC
                    )
                )
                loadMetodosPago()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadMetodosPago() {
        metodoPagoViewModel.listActivos().observe(this) {
            if (it.rpta == 1) {
                with(metodosPago) {
                    clear()
                    addAll(it.body!!)
                }
                binding.cboMetodoPago.setAdapter(
                    ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        metodosPago
                    )
                )
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun presentPerson() {
        when (PersonMovCajaContainer.type) {
            1 -> {
                binding.btnSelectPerson.text = "Cliente:${PersonMovCajaContainer.cliente?.nombre}"
            }
            2 -> {
                binding.btnSelectPerson.text =
                    "Trabajador:${PersonMovCajaContainer.usuario?.nombre}"
            }
            3 -> {
                binding.btnSelectPerson.text =
                    "Proveedor:${PersonMovCajaContainer.proveedor?.nombre}"
            }
        }
    }
}
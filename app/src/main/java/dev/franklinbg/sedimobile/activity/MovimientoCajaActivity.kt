package dev.franklinbg.sedimobile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import dev.franklinbg.sedimobile.databinding.ActivityMovimientoCajaBinding
import dev.franklinbg.sedimobile.dialog.SeleccionePersonaDialog
import dev.franklinbg.sedimobile.model.Caja
import dev.franklinbg.sedimobile.model.ConceptoMovCaja
import dev.franklinbg.sedimobile.model.MetodoPago
import dev.franklinbg.sedimobile.model.MovCaja
import dev.franklinbg.sedimobile.utils.PersonMovCajaContainer
import dev.franklinbg.sedimobile.utils.PersonMovCajaContainer.Companion.cliente
import dev.franklinbg.sedimobile.utils.PersonMovCajaContainer.Companion.proveedor
import dev.franklinbg.sedimobile.utils.PersonMovCajaContainer.Companion.usuario
import dev.franklinbg.sedimobile.utils.UsuarioContainer
import dev.franklinbg.sedimobile.viewmodel.CajaViewModel
import dev.franklinbg.sedimobile.viewmodel.ConceptoMovCajaViewModel
import dev.franklinbg.sedimobile.viewmodel.MetodoPagoViewModel

class MovimientoCajaActivity : AppCompatActivity() {
    private lateinit var currentCaja: Caja
    private lateinit var binding: ActivityMovimientoCajaBinding
    private lateinit var conceptoMovCajaViewModel: ConceptoMovCajaViewModel
    private lateinit var metodoPagoViewModel: MetodoPagoViewModel
    private lateinit var viewModel: CajaViewModel
    private val conceptosMC = ArrayList<ConceptoMovCaja>()
    private val metodosPago = ArrayList<MetodoPago>()
    private var indexConceptoMC = -1
    private var indexMetodoPago = -1
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
            cboConceptoMovCaja.setOnItemClickListener { _, _, pos, _ ->
                tiConceptoMovCaja.isErrorEnabled = false
                indexConceptoMC = pos
            }
            cboMetodoPago.setOnItemClickListener { _, _, pos, _ ->
                tiMetodoPago.isErrorEnabled = false
                indexMetodoPago = pos
            }
            btnRegistrarMov.setOnClickListener {
                if (validate()) {
                    saveMovCaja()
                }
            }
            edtMonto.addTextChangedListener {
                tiMonto.isErrorEnabled = false
            }
            edtDescripcion.addTextChangedListener {
                tiDescripcion.isErrorEnabled = false
            }
        }
    }

    private fun saveMovCaja() {
        val movCaja = MovCaja()
        with(movCaja) {
            caja = this@MovimientoCajaActivity.currentCaja
            usuario = UsuarioContainer.currentUser
            tipoMov = if (binding.rbEntrada.isChecked) 'E' else 'S'
            conceptoMovCaja = conceptosMC[indexConceptoMC]
            metodoPago = metodosPago[indexMetodoPago]
            trabajador = PersonMovCajaContainer.usuario
            cliente = PersonMovCajaContainer.cliente
            proveedor = PersonMovCajaContainer.proveedor
            total = binding.edtMonto.text!!.toString().toDouble()
            descripcion = binding.edtDescripcion.text!!.toString()
            estado = 'P'
        }
        viewModel.saveMovimiento(movCaja).observe(this) {
            Toast.makeText(this@MovimientoCajaActivity, it.message, Toast.LENGTH_SHORT).show()
            if (it.rpta == 1) finish()
        }
    }

    private fun initViewModels() {
        val vmp = ViewModelProvider(this)
        viewModel = vmp[CajaViewModel::class.java]
        conceptoMovCajaViewModel = vmp[ConceptoMovCajaViewModel::class.java]
        metodoPagoViewModel = vmp[MetodoPagoViewModel::class.java]
    }

    private fun loadData() {
        if (UsuarioContainer.currentUser != null) {
            viewModel.getByUserId(UsuarioContainer.currentUser!!.id).observe(this) {
                if (it.rpta == 1) {
                    currentCaja = it.body!!
                } else {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    finish()
                }

            }
        } else {
            finish()
        }
        conceptoMovCajaViewModel.listActivos().observe(this) {
            if (it.rpta == 1) {
                with(conceptosMC) {
                    clear()
                    addAll(it.body!!)
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
                binding.btnSelectPerson.text = "Cliente:${cliente?.nombre}"
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

    private fun validate(): Boolean {
        var valid = true
        with(binding) {
            if (indexConceptoMC == -1) {
                valid = false
                activateError(tiConceptoMovCaja)
            }
            if (indexMetodoPago == -1) {
                valid = false
                activateError(tiMetodoPago)
            }
            if (edtMonto.text!!.isEmpty() || edtMonto.text!!.toString() == "0" || edtMonto.text.toString() == ".") {
                activateError(tiMonto, "monto no válido")
            }
            if (edtDescripcion.text!!.isEmpty()) {
                activateError(tiDescripcion, "debe escribir una descripción")
            }
            if (cliente == null && proveedor == null && usuario == null) {
                valid = false
                Toast.makeText(
                    this@MovimientoCajaActivity,
                    "Debes seleccionar a una persona",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return valid

    }

    private fun activateError(til: TextInputLayout, error: String = "campo abligatorio") {
        til.error = error
    }
}
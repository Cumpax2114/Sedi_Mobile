package dev.franklinbg.sedimobile.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import dev.franklinbg.sedimobile.R
import dev.franklinbg.sedimobile.databinding.DialogPagarContratoBinding
import dev.franklinbg.sedimobile.model.*
import dev.franklinbg.sedimobile.viewmodel.CajaViewModel
import dev.franklinbg.sedimobile.viewmodel.ContratoViewModel
import dev.franklinbg.sedimobile.viewmodel.MetodoPagoViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class PagarContratoDialog(val idCaja: Int, val contrato: Contrato) : DialogFragment() {
    private lateinit var metodoPagoViewModel: MetodoPagoViewModel
    private lateinit var viewModel: ContratoViewModel
    private lateinit var cajaViewModel: CajaViewModel
    private lateinit var binding: DialogPagarContratoBinding
    private lateinit var metodoPagoAdapter: ArrayAdapter<MetodoPago>
    private lateinit var cuotasAdapter: ArrayAdapter<String>
    private val metodosPago = ArrayList<MetodoPago>()
    private var indexMetodoPago = -1
    private var cuotas = 0
    private var total by Delegates.observable(0.0) { _: KProperty<*>, _, newValue: Double ->
        binding.btnPagar.isEnabled = (newValue < montoCierre)
    }
    private var montoCierre = 0.0
    private val detallesCaja = ArrayList<DetalleCaja>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogPagarContratoBinding.inflate(inflater, container, false)
        configure()
        binding.tvCuotaMensual.text = contrato.cuotaMensual.toString()
        initViewModels()
        initAdapters()
        loadData()
        initListeners()
        return binding.root
    }

    private fun configure() {
        requireDialog().window?.setBackgroundDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.background
            )
        )
        requireDialog().window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        requireDialog().window?.attributes?.windowAnimations = R.style.animation
    }

    private fun loadData() {
        metodoPagoViewModel.listActivos().observe(viewLifecycleOwner) {
            if (it.rpta == 1) {
                with(metodosPago) {
                    clear()
                    addAll(it.body!!)
                }
                metodoPagoAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
        cajaViewModel.getCurrentDetails(idCaja).observe(viewLifecycleOwner) {
            if (it.rpta == 1) {
                detallesCaja.addAll(it.body!!)
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener { dismiss() }
        binding.cboMetodoPago.setOnItemClickListener { _, _, index, _ ->
            indexMetodoPago = index
            loadMontoActual()
            if (total != 0.0)
                binding.btnPagar.isEnabled = (total < montoCierre)
        }
        binding.cboCuotas.setOnItemClickListener { _, _, index, _ ->
            cuotas = index + 1
            calcularTotal()
        }
        binding.btnPagar.setOnClickListener {
            val pagos = ArrayList<PagoContrato>()
            repeat(cuotas) {
                PagoContrato().apply {
                    contrato = this@PagarContratoDialog.contrato
                    caja = Caja()
                    caja.id = idCaja
                    metodoPago = metodosPago[indexMetodoPago]
                    fechaPago = Date()
                    montoPagado = total / cuotas
                    pagos.add(this)
                    Toast.makeText(requireContext(), montoPagado.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun loadMontoActual() {
        for (dc in detallesCaja) {
            if (dc.metodoPago.id == metodosPago[indexMetodoPago].id) {
                binding.LinearMontoDisponible.visibility = View.VISIBLE
                montoCierre = dc.montoCierre
                binding.tvMontoDisponible.text = "S/${montoCierre}"
            }
        }
    }

    private fun calcularTotal() {
        binding.LinearTotalPagar.visibility = View.VISIBLE
        total = cuotas * contrato.cuotaMensual
        binding.tvTotalPagar.text = "S/${total}"

    }

    private fun initAdapters() {
        val item = android.R.layout.simple_spinner_dropdown_item
        metodoPagoAdapter = ArrayAdapter(requireContext(), item, metodosPago)
        binding.cboMetodoPago.setAdapter(metodoPagoAdapter)
        val cuotas = ArrayList<String>()
        val cuotasPendientes = contrato.totalCuotas - contrato.cuotasPagadas
        binding.tvCuotasPendientes.text = cuotasPendientes.toString()
        for (i in 1..cuotasPendientes) {
            cuotas.add(i.toString())
        }
        cuotasAdapter = ArrayAdapter(requireContext(), item, cuotas)
        binding.cboCuotas.setAdapter(cuotasAdapter)
    }

    private fun initViewModels() {
        val vmp = ViewModelProvider(this)
        viewModel = vmp[ContratoViewModel::class.java]
        metodoPagoViewModel = vmp[MetodoPagoViewModel::class.java]
        cajaViewModel = vmp[CajaViewModel::class.java]
    }
}
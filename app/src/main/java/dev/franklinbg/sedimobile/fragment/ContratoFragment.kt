package dev.franklinbg.sedimobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import dev.franklinbg.sedimobile.databinding.FragmentContratoBinding
import dev.franklinbg.sedimobile.model.Cliente
import dev.franklinbg.sedimobile.model.TipoContrato
import dev.franklinbg.sedimobile.utils.DecimalDigitsInputFilter
import dev.franklinbg.sedimobile.viewmodel.ClienteViewModel
import dev.franklinbg.sedimobile.viewmodel.ContratoViewModel
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ContratoFragment : Fragment() {
    private lateinit var binding: FragmentContratoBinding
    private lateinit var viewModel: ContratoViewModel
    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var adapter: ArrayAdapter<TipoContrato>
    private lateinit var clienteAdapter: ArrayAdapter<Cliente>
    private val tipos = ArrayList<TipoContrato>()
    private val clientes = ArrayList<Cliente>()
    private var cantCuotas = -1
    private val cuotas = arrayOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12",
        "13",
        "14",
        "15",
        "16",
        "17",
        "18",
        "19",
        "20",
        "21",
        "22",
        "23",
        "24"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContratoBinding.inflate(inflater, container, false)
        val vmp = ViewModelProvider(this)
        viewModel = vmp[ContratoViewModel::class.java]
        clienteViewModel = vmp[ClienteViewModel::class.java]
        initListeners()
        initAdapter()
        loadData()
        return binding.root
    }

    private fun initAdapter() {
        adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            tipos
        )
        binding.cboTipoContrato.setAdapter(adapter)

        clienteAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, clientes)
        binding.cboCliente.setAdapter(clienteAdapter)
    }

    private fun loadData() {
        viewModel.listTiposActivos().observe(viewLifecycleOwner) {
            if (it.rpta == 1) {
                tipos.clear()
                tipos.addAll(it.body!!)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
        clienteViewModel.listAll().observe(viewLifecycleOwner) {
            if (it.rpta == 1) {
                clientes.clear()
                clientes.addAll(it.body!!)
                clienteAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
        binding.cboCuotas.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                cuotas
            )
        )
    }

    private fun initListeners() {
        with(binding) {
            cboCuotas.setOnItemClickListener { _, _, i, _ ->
                cantCuotas = i + 1
            }
            edtTotal.filters = arrayOf(DecimalDigitsInputFilter(11, 2))
            val constraint =
                CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now()).build()
            dpFechaInicio.setOnFocusChangeListener { _, b ->
                if (b) {
                    val picker =
                        MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Seleccione la fecha de inicio del contrato")
                            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                            .setCalendarConstraints(constraint)
                            .build()
                    picker.addOnPositiveButtonClickListener {
                        dpFechaInicio.setText(
                            SimpleDateFormat(
                                "dd-MM-yyyy",
                                Locale.US
                            ).format(it)
                        )
                        tiFechaInicio.isErrorEnabled = false
                    }
                    picker.show(childFragmentManager, "")
                    edtTotal.requestFocus()
                }
            }
            dpFechaFin.setOnFocusChangeListener { _, b ->
                if (b) {
                    val picker =
                        MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Seleccione la fecha de fin de contrato")
                            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                            .setCalendarConstraints(constraint)
                            .build()
                    picker.addOnPositiveButtonClickListener {
                        dpFechaFin.setText(
                            SimpleDateFormat(
                                "dd-MM-yyyy",
                                Locale.US
                            ).format(it)
                        )
                        tiFechaInicio.isErrorEnabled = false
                    }
                    picker.show(childFragmentManager, "")
                    edtTotal.requestFocus()
                }
            }
            btnCalcularCuota.setOnClickListener {
                var valid = true
                if (cantCuotas == -1) {
                    valid = false
                    Toast.makeText(requireContext(), "Selecciona una cuota", Toast.LENGTH_SHORT)
                        .show()
                }
                if (edtTotal.text!!.toString().isEmpty()) {
                    valid = false
                    Toast.makeText(
                        requireContext(),
                        "Ingrese un monto para el total",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                if (valid) {
                    val decimalFormat = DecimalFormat("##.##")
                    try {
                        val total = decimalFormat.parse(edtTotal.text.toString()).toDouble()
                        val totalCuotas = total / cantCuotas
                        edtTotalCuota.setText(decimalFormat.format(totalCuotas))
                    } catch (pe: ParseException) {
                        Toast.makeText(requireContext(), pe.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
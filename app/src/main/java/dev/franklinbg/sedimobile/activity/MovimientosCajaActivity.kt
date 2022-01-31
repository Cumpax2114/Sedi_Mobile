package dev.franklinbg.sedimobile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.franklinbg.sedimobile.adapter.MovimientoCajaAdapter
import dev.franklinbg.sedimobile.communication.MovimientoCajaCommunication
import dev.franklinbg.sedimobile.databinding.ActivityMovimientosCajaBinding
import dev.franklinbg.sedimobile.model.Apertura
import dev.franklinbg.sedimobile.viewmodel.CajaViewModel

class MovimientosCajaActivity : AppCompatActivity(), MovimientoCajaCommunication {
    private lateinit var binding: ActivityMovimientosCajaBinding
    private lateinit var viewModel: CajaViewModel
    private lateinit var adapter: MovimientoCajaAdapter
    private lateinit var aperturasAdapter: ArrayAdapter<Apertura>
    private val aperturas = ArrayList<Apertura>()
    private var idApertura = -1
    private var idCaja = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovimientosCajaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        idCaja = intent.getIntExtra("idCaja", 0)
        if (idCaja != 0) {
            initViewModels()
            initAdapter()
            loadData()
            initListeners()
        } else {
            finish()
        }
    }

    private fun initViewModels() {
        val vmp = ViewModelProvider(this)
        viewModel = vmp[CajaViewModel::class.java]
    }

    private fun loadData() {
        viewModel.getMovimientosByCajaId(idCaja).observe(this) {
            if (it.rpta == 1) {
                adapter.updateItems(it.body!!)
                Toast.makeText(
                    this,
                    "Estas viendo el listado general actualmente",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.getAperturas(idCaja).observe(this) {
            if (it.rpta == 1) {
                with(aperturas) {
                    clear()
                    addAll(it.body!!)
                }
                aperturasAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initAdapter() {
        adapter = MovimientoCajaAdapter(this)
        binding.rcvMovimientos.apply {
            layoutManager = LinearLayoutManager(this@MovimientosCajaActivity)
            adapter = this@MovimientosCajaActivity.adapter
        }
        aperturasAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, aperturas)
        binding.cboAperturas.setAdapter(aperturasAdapter)
    }

    override fun anularMovimiento(id: Int) {
        viewModel.anularMovimiento(id).observe(this) {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            if (it.rpta == 1) {
                loadData()
            }
        }
    }

    private fun initListeners() {
        binding.chkHabilitarFiltro.setOnCheckedChangeListener { _, isChecked ->
            binding.tiAperturas.isEnabled = isChecked
            if (isChecked && idApertura != -1) {
                filterList()
            } else {
                loadData()
            }
        }
        binding.cboAperturas.setOnItemClickListener { _, _, index, _ ->
            idApertura = aperturas[index].id
            if (binding.chkHabilitarFiltro.isChecked) {
                filterList()
            }
        }
    }

    private fun filterList() {
        viewModel.listMovimientosByAperturaId(idApertura).observe(this) {
            if (it.rpta == 1) {
                adapter.updateItems(it.body!!)
                Toast.makeText(this, "Filtrado realizado correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
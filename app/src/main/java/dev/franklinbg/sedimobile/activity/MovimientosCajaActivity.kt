package dev.franklinbg.sedimobile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.franklinbg.sedimobile.adapter.MovimientoCajaAdapter
import dev.franklinbg.sedimobile.communication.MovimientoCajaCommunication
import dev.franklinbg.sedimobile.databinding.ActivityMovimientosCajaBinding
import dev.franklinbg.sedimobile.viewmodel.CajaViewModel

class MovimientosCajaActivity : AppCompatActivity(), MovimientoCajaCommunication {
    private lateinit var binding: ActivityMovimientosCajaBinding
    private lateinit var viewModel: CajaViewModel
    private lateinit var adapter: MovimientoCajaAdapter
    private var idCaja = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovimientosCajaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        idCaja = intent.getIntExtra("idCaja", 0)
        if (idCaja != 0) {
            viewModel = ViewModelProvider(this)[CajaViewModel::class.java]
            initAdapter()
            loadData()
        } else {
            finish()
        }
    }

    private fun loadData() {
        viewModel.getMovimientosByCajaId(idCaja).observe(this) {
            if (it.rpta == 1) {
                adapter.updateItems(it.body!!)
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
    }

    override fun anularMovimiento(id: Int) {
        viewModel.anularMovimiento(id).observe(this) {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            if (it.rpta == 1) {
                loadData()
            }
        }
    }
}
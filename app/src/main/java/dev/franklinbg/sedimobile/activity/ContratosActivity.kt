package dev.franklinbg.sedimobile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.franklinbg.sedimobile.adapter.ContratoAdapter
import dev.franklinbg.sedimobile.databinding.ActivityContratosBinding
import dev.franklinbg.sedimobile.viewmodel.ContratoViewModel

class ContratosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContratosBinding
    private lateinit var viewModel: ContratoViewModel
    private lateinit var adapter: ContratoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContratosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ContratoViewModel::class.java]
        initAdapter()
        loadData()
    }

    private fun loadData() {
        viewModel.listAll().observe(this) {
            if (it.rpta == 1) {
                adapter.updateItems(it.body!!)
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initAdapter() {
        adapter = ContratoAdapter()
        binding.rcvContratos.apply {
            layoutManager = LinearLayoutManager(this@ContratosActivity)
            adapter = this@ContratosActivity.adapter
        }
    }
}
package dev.franklinbg.sedimobile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.franklinbg.sedimobile.adapter.ContratoAdapter
import dev.franklinbg.sedimobile.communication.ContratoCommunication
import dev.franklinbg.sedimobile.databinding.ActivityListarContratosBinding
import dev.franklinbg.sedimobile.dialog.PagarContratoDialog
import dev.franklinbg.sedimobile.model.Contrato
import dev.franklinbg.sedimobile.viewmodel.ContratoViewModel
import kotlin.properties.Delegates

class ListarContratosActivity : AppCompatActivity(), ContratoCommunication {
    private lateinit var binding: ActivityListarContratosBinding
    private lateinit var viewModel: ContratoViewModel
    private lateinit var adapter: ContratoAdapter
    private var idCaja by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarContratosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        idCaja=intent.getIntExtra("idCaja",0)
        if(idCaja!=0){
            viewModel = ViewModelProvider(this)[ContratoViewModel::class.java]
            initAdapter()
            loadData()
        }else{
            Toast.makeText(this,"id de caja no encontrado",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun loadData() {
        viewModel.listAll().observe(this) {
            if (it.rpta == 1) {
                adapter.updateItems(it.body!!)
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initAdapter() {
        adapter = ContratoAdapter(this)
        binding.rcvContratos.apply {
            layoutManager = LinearLayoutManager(this@ListarContratosActivity)
            adapter = this@ListarContratosActivity.adapter
        }
    }

    override fun pagar(contrato: Contrato) =
        PagarContratoDialog(idCaja,contrato).show(supportFragmentManager, "")
}
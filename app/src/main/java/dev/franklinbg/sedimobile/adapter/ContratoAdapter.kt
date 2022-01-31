package dev.franklinbg.sedimobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.franklinbg.sedimobile.communication.ContratoCommunication
import dev.franklinbg.sedimobile.databinding.ItemContratoBinding
import dev.franklinbg.sedimobile.model.Contrato
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ContratoAdapter(val communication: ContratoCommunication) :
    RecyclerView.Adapter<ContratoAdapter.ViewHolder>() {
    private val contratos = ArrayList<Contrato>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemContratoBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        communication
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setItem(contratos[position])

    override fun getItemCount(): Int = contratos.size
    fun updateItems(contratos: ArrayList<Contrato>) {
        with(this.contratos) {
            clear()
            addAll(contratos)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemContratoBinding, val communication: ContratoCommunication) :
        RecyclerView.ViewHolder(binding.root) {
        fun setItem(contrato: Contrato) {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            with(binding) {
                tvFechaInicio.text = sdf.format(contrato.fechaInicio)
                tvFechaFin.text = sdf.format(contrato.fechaTermino)
                tvTotal.text = contrato.totalContrato.toString()
                tvCuotas.text = contrato.totalCuotas.toString()
                tvCliente.text = contrato.cliente.nombre
                if (contrato.totalCuotas == contrato.cuotasPagadas) {
                    btnPagar.isEnabled = false
                    btnPagar.text = "se terminó de pagar"
                }
                btnPagar.setOnClickListener {
                    communication.pagar(contrato)
                }

            }
        }
    }
}
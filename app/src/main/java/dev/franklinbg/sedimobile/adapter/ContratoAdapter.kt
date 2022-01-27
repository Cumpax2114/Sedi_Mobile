package dev.franklinbg.sedimobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.franklinbg.sedimobile.databinding.ItemContratoBinding
import dev.franklinbg.sedimobile.model.Contrato
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ContratoAdapter : RecyclerView.Adapter<ContratoAdapter.ViewHolder>() {
    private val contratos = ArrayList<Contrato>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemContratoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    class ViewHolder(val binding: ItemContratoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(contrato: Contrato) {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            with(binding) {
                tvFechaInicio.text = sdf.format(contrato.fechaInicio)
                tvFechaFin.text = sdf.format(contrato.fechaTermino)
                tvTotal.text = contrato.totalContrato.toString()
                tvCuotas.text = contrato.cuotaMensual.toString()
                tvCliente.text = contrato.cliente.nombre
            }
        }
    }
}
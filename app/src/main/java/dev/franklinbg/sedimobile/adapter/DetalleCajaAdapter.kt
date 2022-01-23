package dev.franklinbg.sedimobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.franklinbg.sedimobile.databinding.ItemDetalleCajaBinding
import dev.franklinbg.sedimobile.model.DetalleCaja

class DetalleCajaAdapter : RecyclerView.Adapter<DetalleCajaAdapter.ViewHolder>() {
    val detalles = ArrayList<DetalleCaja>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemDetalleCajaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setItem(detalles[position])

    override fun getItemCount(): Int = detalles.size
    fun updateItems(detalles: ArrayList<DetalleCaja>) {
        with(this.detalles) {
            clear()
            addAll(detalles)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemDetalleCajaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(detalle: DetalleCaja) {
            with(binding) {
                tvMetodoPago.text = "${detalle.metodoPago.nombre}:"
                tvMontoCierre.text = "S/${detalle.montoCierre}"
            }
        }
    }
}
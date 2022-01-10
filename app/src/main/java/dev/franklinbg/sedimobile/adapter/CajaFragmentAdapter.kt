package dev.franklinbg.sedimobile.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.franklinbg.sedimobile.activity.CajaActivity
import dev.franklinbg.sedimobile.activity.MovimientoCajaActivity
import dev.franklinbg.sedimobile.activity.ReportesActivity
import dev.franklinbg.sedimobile.databinding.ItemCajaFragmentBinding
import dev.franklinbg.sedimobile.utils.SeccionCaja

class CajaFragmentAdapter(private val secciones: List<SeccionCaja>) :
    RecyclerView.Adapter<CajaFragmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCajaFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setItem(secciones[position])

    override fun getItemCount(): Int = secciones.size
    class ViewHolder(val binding: ItemCajaFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(seccion: SeccionCaja) {
            with(binding) {
                imgSeccion.setImageResource(seccion.img)
                tvTitulo.text = seccion.nombre
                itemView.setOnClickListener {

                    itemView.context.startActivity(
                        Intent(
                            itemView.context,
                            when (seccion.id) {
                                1 -> CajaActivity::class.java
                                2 -> MovimientoCajaActivity::class.java
                                else -> ReportesActivity::class.java
                            }

                        )
                    )
                }
            }
        }
    }
}
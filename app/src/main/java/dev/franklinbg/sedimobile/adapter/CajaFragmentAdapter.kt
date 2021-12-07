package dev.franklinbg.sedimobile.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.franklinbg.sedimobile.R
import dev.franklinbg.sedimobile.activity.CajaActivity
import dev.franklinbg.sedimobile.utils.SeccionCaja

class CajaFragmentAdapter(val secciones: List<SeccionCaja>) :
    RecyclerView.Adapter<CajaFragmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_caja_fragment, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setItem(secciones[position])

    override fun getItemCount(): Int = secciones.size
    class ViewHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val imgSeccion = itemView.findViewById<ImageView>(R.id.imgSeccion)
        val tvTitulo = itemView.findViewById<TextView>(R.id.titulo)
        fun setItem(seccion: SeccionCaja) {
            imgSeccion.setImageResource(seccion.img)
            tvTitulo.text = seccion.nombre
            itemView.setOnClickListener {
                when (seccion.id) {
                    1 -> {
                        itemView.context.startActivity(
                            Intent(
                                itemView.context,
                                CajaActivity::class.java
                            )
                        )
                    }
                }
            }
        }
    }
}
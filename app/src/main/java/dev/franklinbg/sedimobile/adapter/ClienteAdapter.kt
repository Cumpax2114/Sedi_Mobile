package dev.franklinbg.sedimobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.franklinbg.sedimobile.R
import dev.franklinbg.sedimobile.databinding.ItemClienteBinding
import dev.franklinbg.sedimobile.model.Cliente

class ClienteAdapter : RecyclerView.Adapter<ClienteAdapter.ViewHolder>() {
    private var clientes = ArrayList<Cliente>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemClienteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setItem(clientes[position])

    override fun getItemCount(): Int = clientes.size
    fun updateItems(clientes: ArrayList<Cliente>) {
        with(this.clientes) {
            clear()
            addAll(clientes)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemClienteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(cliente: Cliente) {
            with(binding) {
                tvEstado.text = if (cliente.estado == 'A') "Activo" else "Inactivo"
                val colorId = if (cliente.estado == 'A') {
                    R.color.green_dark
                } else {
                    R.color.red
                }
                cardEstado.setBackgroundColor(ContextCompat.getColor(itemView.context, colorId))
                tvNombre.text = cliente.nombre
                tvTelefono.text = cliente.telefono
                tvDieccion.text = cliente.direccion
            }
        }
    }
}
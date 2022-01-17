package dev.franklinbg.sedimobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.franklinbg.sedimobile.communication.SeleccionePersonaDialogCommunication
import dev.franklinbg.sedimobile.databinding.ItemTrabajadorMovCajaBinding
import dev.franklinbg.sedimobile.model.Usuario

class TrabajadorMovCajaAdapter(val communication: SeleccionePersonaDialogCommunication) :
    RecyclerView.Adapter<TrabajadorMovCajaAdapter.ViewHolder>() {
    private val trabajadores = ArrayList<Usuario>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemTrabajadorMovCajaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), communication
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setItem(trabajadores[position])

    override fun getItemCount(): Int = trabajadores.size
    fun updateItems(usuarios: ArrayList<Usuario>) {
        with(trabajadores) {
            clear()
            addAll(usuarios)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: ItemTrabajadorMovCajaBinding,
        val communication: SeleccionePersonaDialogCommunication
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun setItem(usuario: Usuario) {
            with(binding) {
                tvNombre.text = usuario.nombre
                tvTelFono.text = usuario.telefono
                btnSelectTrabajador.setOnClickListener {
                    communication.addUsuario(usuario)
                }
            }
        }
    }
}
package dev.franklinbg.sedimobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.franklinbg.sedimobile.communication.SeleccionePersonaDialogCommunication
import dev.franklinbg.sedimobile.databinding.ItemClienteMovCajaBinding
import dev.franklinbg.sedimobile.model.Cliente

class ClienteMovCajaAdapter(private val communication: SeleccionePersonaDialogCommunication) :
    RecyclerView.Adapter<ClienteMovCajaAdapter.ViewHolder>() {
    val clientes = ArrayList<Cliente>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemClienteMovCajaBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        communication
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

    class ViewHolder(
        val binding: ItemClienteMovCajaBinding,
        private val communication: SeleccionePersonaDialogCommunication
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun setItem(cliente: Cliente) {
            with(binding) {
                tvDocumento.text = cliente.documento
                tvNombreRZ.text = cliente.nombre
                tvDireccion.text = cliente.direccion
                btnSelectCliente.setOnClickListener {
                    communication.addCliente(cliente)
                }
            }
        }
    }
}
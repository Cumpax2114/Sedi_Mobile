package dev.franklinbg.sedimobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.franklinbg.sedimobile.communication.SeleccionePersonaDialogCommunication
import dev.franklinbg.sedimobile.databinding.ItemProveedorMovCajaBinding
import dev.franklinbg.sedimobile.model.Proveedor

class ProveedorMovCajaAdapter(val communication: SeleccionePersonaDialogCommunication) :
    RecyclerView.Adapter<ProveedorMovCajaAdapter.ViewHolder>() {
    private val proveedores = ArrayList<Proveedor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemProveedorMovCajaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), communication
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setItem(proveedores[position])

    override fun getItemCount(): Int = proveedores.size
    fun updateItems(proveedores: ArrayList<Proveedor>) {
        with(this.proveedores) {
            clear()
            addAll(proveedores)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: ItemProveedorMovCajaBinding,
        val communication: SeleccionePersonaDialogCommunication
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun setItem(proveedor: Proveedor) {
            with(binding) {
                tvDocumento.text = proveedor.documento
                tvRz.text = proveedor.nombre
                tvTelFono.text = proveedor.telefono
                tvMontoCompra.text = "S/${proveedor.monto_compra}"
                tvDireccion.text = proveedor.direccion
                btnSelectProveedor.setOnClickListener {
                    communication.addProveedor(proveedor)
                }
            }
        }
    }
}
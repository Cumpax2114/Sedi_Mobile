package dev.franklinbg.sedimobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import dev.franklinbg.sedimobile.communication.AddDetailCommunication
import dev.franklinbg.sedimobile.databinding.ItemMetodoPagoBinding
import dev.franklinbg.sedimobile.model.Caja
import dev.franklinbg.sedimobile.model.DetalleCaja
import dev.franklinbg.sedimobile.model.MetodoPago

class MetodosPagoCajaAdapter(val communication: AddDetailCommunication, var idCaja: Int = 0) :
    RecyclerView.Adapter<MetodosPagoCajaAdapter.ViewHolder>() {
    val metodosPago = ArrayList<MetodoPago>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemMetodoPagoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), idCaja, communication
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setItem(metodosPago[position])

    override fun getItemCount(): Int = metodosPago.size
    fun updateItems(metodosPago: ArrayList<MetodoPago>) {
        with(this.metodosPago) {
            clear()
            addAll(metodosPago)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(
        private val binding: ItemMetodoPagoBinding,
        var idCaja: Int,
        val communication: AddDetailCommunication
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun setItem(metodoPago: MetodoPago) {
            with(binding) {
                tiMetodoPago.hint = metodoPago.nombre
                edtMonto.hint = metodoPago.nombre
                edtMonto.addTextChangedListener {
                    tiMetodoPago.isErrorEnabled = false
                }
                btnAdd.setOnClickListener {
                    if (edtMonto.text!!.isNotEmpty()) {
                        val detalle = DetalleCaja()
                        detalle.metodoPago = metodoPago
                        detalle.monto = edtMonto.text.toString().toDouble()
                        detalle.caja = Caja()
                        detalle.caja.id = idCaja
                        Toast.makeText(
                            itemView.context,
                            communication.addDetail(detalle),
                            Toast.LENGTH_SHORT
                        ).show()
                        edtMonto.text!!.clear()
                    } else {
                        tiMetodoPago.error = "Ingrese un monto válido"
                    }
                }
            }
        }
    }
}
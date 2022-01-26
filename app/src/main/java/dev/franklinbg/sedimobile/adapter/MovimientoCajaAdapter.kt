package dev.franklinbg.sedimobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.franklinbg.sedimobile.R
import dev.franklinbg.sedimobile.communication.MovimientoCajaCommunication
import dev.franklinbg.sedimobile.databinding.ItemMovimientoCajaBinding
import dev.franklinbg.sedimobile.model.MovCaja

class MovimientoCajaAdapter(val communication: MovimientoCajaCommunication) :
    RecyclerView.Adapter<MovimientoCajaAdapter.ViewHolder>() {
    private val movimientos = ArrayList<MovCaja>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemMovimientoCajaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), communication
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setItem(movimientos[position])

    override fun getItemCount(): Int = movimientos.size
    fun updateItems(movimientos: ArrayList<MovCaja>) {
        this.movimientos.apply {
            clear()
            addAll(movimientos)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: ItemMovimientoCajaBinding,
        val communication: MovimientoCajaCommunication
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun setItem(movCaja: MovCaja) {
            with(binding) {
                tvEstado.text = if (movCaja.estado == 'P') "Pagado" else "Anulado"
                cardEstado.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context, if (movCaja.estado == 'P') {
                            R.color.green_dark
                        } else {
                            R.color.red
                        }
                    )
                )
                tvCMC.text = movCaja.conceptoMovCaja.nombre
                tvTipoMov.text = if (movCaja.tipoMov == 'E') "Entrada" else "Salida"
                tvMetodoPago.text = movCaja.metodoPago.nombre
                tvPersona.text = when {
                    movCaja.cliente != null -> {
                        movCaja.cliente.nombre
                    }
                    movCaja.trabajador != null -> {
                        movCaja.trabajador.nombre
                    }
                    movCaja.usuario != null -> {
                        movCaja.usuario.nombre
                    }
                    else -> {
                        "No name"
                    }
                }
                tvTotal.text = "S/${movCaja.total}"
                btnAnular.isEnabled = movCaja.estado == 'P'
                btnAnular.alpha = if (movCaja.estado == 'P') 1f else 0.5f
                btnAnular.setOnClickListener {
                    communication.anularMovimiento(movCaja.id)
                }
            }
        }
    }
}
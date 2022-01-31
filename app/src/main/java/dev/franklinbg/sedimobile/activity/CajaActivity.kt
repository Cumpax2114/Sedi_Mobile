package dev.franklinbg.sedimobile.activity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dev.franklinbg.sedimobile.adapter.DetalleCajaAdapter
import dev.franklinbg.sedimobile.adapter.MetodosPagoCajaAdapter
import dev.franklinbg.sedimobile.communication.AddDetailCommunication
import dev.franklinbg.sedimobile.databinding.ActivityCajaBinding
import dev.franklinbg.sedimobile.model.Caja
import dev.franklinbg.sedimobile.model.DetalleCaja
import dev.franklinbg.sedimobile.model.MetodoPago
import dev.franklinbg.sedimobile.model.dto.CajaWithDetallesDTO
import dev.franklinbg.sedimobile.utils.UsuarioContainer
import dev.franklinbg.sedimobile.viewmodel.CajaViewModel
import dev.franklinbg.sedimobile.viewmodel.MetodoPagoViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CajaActivity : AppCompatActivity(), AddDetailCommunication {
    private lateinit var binding: ActivityCajaBinding
    private var currentCaja: Caja? = null
    private lateinit var viewModel: CajaViewModel
    private lateinit var metodoPagoViewModel: MetodoPagoViewModel
    private lateinit var adapter: MetodosPagoCajaAdapter
    private lateinit var detallesAdapter: DetalleCajaAdapter
    private lateinit var ultimosDetallesAdapter: DetalleCajaAdapter
    private val detallesApertura = ArrayList<DetalleCaja>()
    private var metodosPago = ArrayList<MetodoPago>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCajaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initApdater()
        initListeners()
        loadData()
    }

    private fun initViewModel() {
        val vmp = ViewModelProvider(this)
        viewModel = vmp[CajaViewModel::class.java]
        metodoPagoViewModel = vmp[MetodoPagoViewModel::class.java]
    }

    private fun initApdater() {
        adapter = MetodosPagoCajaAdapter(this)
        detallesAdapter = DetalleCajaAdapter()
        ultimosDetallesAdapter = DetalleCajaAdapter()
        with(binding.rcvDetalles) {
            layoutManager = LinearLayoutManager(this@CajaActivity)
            adapter = detallesAdapter
        }
        with(binding.rcvMTodosPago) {
            layoutManager = LinearLayoutManager(this@CajaActivity)
            adapter = this@CajaActivity.adapter
        }
        with(binding.rcvUltimosDetalles) {
            layoutManager = LinearLayoutManager(this@CajaActivity)
            adapter = this@CajaActivity.ultimosDetallesAdapter
        }
    }

    private fun loadData() {
        if (UsuarioContainer.currentUser != null) {
            viewModel.getByUserId(UsuarioContainer.currentUser!!.id).observe(this) {
                if (it.rpta == 1) {
                    currentCaja = it.body!!
                    adapter.idCaja = it.body!!.id
                    if (currentCaja!!.estado == 'C') {
                        metodoPagoViewModel.listActivos().observe(this) { resp ->
                            if (resp.rpta == 1) {
                                metodosPago = resp.body!!
                                adapter.updateItems(resp.body!!)
                            } else {
                                Toast.makeText(this, resp.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                        viewModel.listUltimosDetalles(currentCaja!!.id).observe(this) { resp ->
                            if (resp.rpta == 1) {
                                ultimosDetallesAdapter.updateItems(resp.body!!)
                            } else {
                                Toast.makeText(this, resp.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        viewModel.getCurrentDetails(currentCaja!!.id).observe(this) { detailsR ->
                            if (detailsR.rpta == 1) {
                                detallesAdapter.updateItems(detailsR.body!!)
                            } else {
                                Toast.makeText(this, detailsR.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    showData()
                } else {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    finish()
                }

            }
        } else {
            finish()
        }
    }

    private fun showData() {
        val simpleDatFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
        simpleDatFormat.timeZone = TimeZone.getTimeZone("America/Lima")
        with(binding) {
            tvFechaApertura.text = simpleDatFormat.format(currentCaja!!.fechaApertura)
            tvFechaCierre.text = simpleDatFormat.format(currentCaja!!.fechaCierre)
            if (currentCaja!!.estado == 'C') {
                cardApertura.visibility = View.VISIBLE
                cardUltimosDetalles.visibility = View.VISIBLE
                cardCierre.visibility = View.GONE
            } else {
                cardApertura.visibility = View.GONE
                cardUltimosDetalles.visibility = View.GONE
                cardCierre.visibility = View.VISIBLE
            }
        }
    }

    private fun initListeners() {
        with(binding) {
            btnChangeCajaStatus.setOnClickListener {
                if (detallesApertura.size == metodosPago.size) {
                    var montos = ""
                    for (detalle in detallesApertura) {
                        montos += "${detalle.metodoPago.nombre}:${detalle.monto}\n"
                    }
                    MaterialAlertDialogBuilder(this@CajaActivity).setTitle("confirmar apertura de caja")
                        .setMessage("estas seguro de aperturar esta caja? los montos a ingresar son los siguientes\n$montos")
                        .setPositiveButton("confirmar") { _: DialogInterface, _ ->
                            val dto = CajaWithDetallesDTO()
                            dto.idCaja = currentCaja!!.id
                            dto.detalles = detallesApertura
                            viewModel.open(dto).observe(this@CajaActivity) {
                                if (it.rpta == 1) {
                                    Snackbar.make(
                                        btnChangeCajaStatus.rootView,
                                        it.message,
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                    detallesApertura.clear()
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@CajaActivity,
                                        it.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }.setNegativeButton("cancelar") { dialogInterface: DialogInterface, _ ->
                            dialogInterface.dismiss()
                        }.show()
                } else {
                    Toast.makeText(
                        this@CajaActivity,
                        "rellena todos los metodos de pago por favor",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            btnClose.setOnClickListener {
                MaterialAlertDialogBuilder(this@CajaActivity).setTitle("Cierre de caja")
                    .setMessage("estas seguro de cerra esta caja?\nno podrás hacer movimientos hasta que la vuelvas a abrir")
                    .setPositiveButton("Si,cerrar") { dialogInterface, _ ->
                        viewModel.close(currentCaja!!.id).observe(this@CajaActivity) {
                            Toast.makeText(this@CajaActivity, it.message, Toast.LENGTH_SHORT).show()
                            if (it.rpta == 1) finish()
                            dialogInterface.dismiss()
                        }
                    }
                    .setNegativeButton("no,cancelar") { dialogInterface, _ ->
                        dialogInterface.dismiss()
                    }.show()
            }
        }
    }

    override fun addDetail(detail: DetalleCaja): String {
        if (detallesApertura.isNotEmpty()) {
            var detalleEncontrado: DetalleCaja? = null
            for (detalle in detallesApertura) {
                if (detail.metodoPago.id == detalle.metodoPago.id) {
                    detalleEncontrado = detalle
                    break
                }
            }
            return if (detalleEncontrado == null) {
                detallesApertura.add(detail)
                "Detalle agregado correctamente"
            } else {
                detallesApertura[detallesApertura.indexOf(detalleEncontrado)] = detail
                "Detalle de '${detalleEncontrado.metodoPago.nombre}' actualizado correctamente"
            }
        } else {
            detallesApertura.add(detail)
            return "Detalle agregado correctamente"
        }
    }

}
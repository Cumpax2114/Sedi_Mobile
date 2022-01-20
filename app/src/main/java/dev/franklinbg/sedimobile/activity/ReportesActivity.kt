package dev.franklinbg.sedimobile.activity

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.franklinbg.sedimobile.databinding.ActivityReportesBinding
import dev.franklinbg.sedimobile.model.Apertura
import dev.franklinbg.sedimobile.model.Caja
import dev.franklinbg.sedimobile.utils.UsuarioContainer
import dev.franklinbg.sedimobile.viewmodel.CajaViewModel
import java.io.File
import java.io.FileOutputStream
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReportesActivity : AppCompatActivity() {
    private val permReqLuncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(
                this,
                if (it) "Gracias por concedernos el permiso,oprime el boton nuevamente" else "Permiso denegado,no podemos continuar",
                Toast.LENGTH_SHORT
            ).show()
        }
    private lateinit var binding: ActivityReportesBinding
    private lateinit var viewModel: CajaViewModel
    private lateinit var currentCaja: Caja
    private lateinit var fechaApertura: Date
    private val aperturas = ArrayList<Apertura>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CajaViewModel::class.java]
        check()
        binding.cbofechaApertura.setOnItemClickListener { _, _, index, _ ->
            fechaApertura = aperturas[index].fechaApertura
            binding.btnExport.isEnabled = true
        }
        binding.btnExport.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                downLoadPDF()
            } else {
                SweetAlertDialog(
                    this,
                    SweetAlertDialog.WARNING_TYPE
                ).setTitleText("concedenos el permiso")
                    .setContentText("debido a que vamos a descargar un archivo,requerimos del acceso al almacenamiento interno para poder guardarlo")
                    .setConfirmButton("Vale") {
                        it.dismissWithAnimation()
                        permReqLuncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                    .setCancelButton("quizás mas tarde") {
                        it.dismissWithAnimation()
                    }.show()
            }

        }
    }

    private fun check() {
        if (UsuarioContainer.currentUser != null) {
            viewModel.getByUserId(UsuarioContainer.currentUser!!.id).observe(this) {
                if (it.rpta == 1) {
                    currentCaja = it.body!!
                    loadData()
                } else {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        } else {
            finish()
        }
    }

    private fun loadData() {
        viewModel.getAperturas(currentCaja.id).observe(this) {
            if (it.rpta == 1) {
                with(aperturas) {
                    clear()
                    addAll(it.body!!)
                }
                binding.cbofechaApertura.setAdapter(
                    ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_dropdown_item, aperturas
                    )
                )
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun downLoadPDF() {
        binding.btnExport.isEnabled = false
        binding.btnExport.text = "Espera un momento . . ."
        binding.cbofechaApertura.isEnabled = false
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        viewModel.export(currentCaja.id, sdf.format(fechaApertura)).observe(this) {
            if (it.rpta == 1) {
                if (it.rpta == 1) {
                    binding.btnExport.isEnabled = true
                    binding.btnExport.text = "Generar PDF"
                    try {
                        var folderCreated = true
                        val path =
                            this.getExternalFilesDir("/reports")!!
                        if (!path.exists()) {
                            if (!path.mkdirs()) {
                                folderCreated = false
                                Toast.makeText(
                                    this,
                                    "no se pudo crear la carpeta para guardar los archivos,inténtalo mas tarde",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                        if (folderCreated) {
                            val bytes = it.body!!.bytes()
                            val file = File(path, "sediReport_${Date().time}.pdf")
                            val fileOutputStream = FileOutputStream(file)
                            fileOutputStream.write(bytes)
                            fileOutputStream.close()
                            MaterialAlertDialogBuilder(this).setTitle("Archivo guardado correctamente")
                                .setMessage("puedes encontralo en \n${file.absolutePath}")
                                .setNeutralButton("Listo!") { dialogInterface, _ ->
                                    dialogInterface.dismiss()
                                }
                                .setPositiveButton("Visualizar ahora") { _, _ ->

                                    val i = Intent(this, VisorPDFActivity::class.java)
                                    i.putExtra("pdf", bytes)
                                    startActivity(i)
                                }.show()
                        }
                    } catch (e: Exception) {
                        MaterialAlertDialogBuilder(this).setTitle("No se pudo escribir el archivo")
                            .setMessage(e.message!!)
                            .setNeutralButton("Vale!") { dialogInterface: DialogInterface, _: Int ->
                                dialogInterface.dismiss()
                            }.show()
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
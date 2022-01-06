package dev.franklinbg.sedimobile.dialog

import android.os.AsyncTask
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import dev.franklinbg.sedimobile.R
import dev.franklinbg.sedimobile.databinding.DialogRegistrarClienteBinding
import dev.franklinbg.sedimobile.utils.SunatEmpresa
import dev.franklinbg.sedimobile.utils.ReniecPerson
import dev.franklinbg.sedimobile.viewmodel.search.BuscarPorDNIViewModel
import dev.franklinbg.sedimobile.viewmodel.search.BuscarPorRUCViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.app.Activity
import android.content.DialogInterface
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import dev.franklinbg.sedimobile.model.Cliente
import dev.franklinbg.sedimobile.viewmodel.ClienteViewModel


class RegistrarClienteDialog : DialogFragment() {
    private val tiposDoc = listOf("DNI", "RUC", "OTRO")
    private var indexTd = -1
    private var validated = false
    private lateinit var viewModel: ClienteViewModel
    private lateinit var searchDNIViewModel: BuscarPorDNIViewModel
    private lateinit var searchRUCViewModel: BuscarPorRUCViewModel
    private lateinit var binding: DialogRegistrarClienteBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogRegistrarClienteBinding.inflate(inflater, container, false)
        configure()
        initViewModels()
        initListeners()
        return binding.root
    }

    private fun initViewModels() {
        val vmp = ViewModelProvider(this)
        viewModel = vmp[ClienteViewModel::class.java]
        searchDNIViewModel = vmp[BuscarPorDNIViewModel::class.java]
        searchRUCViewModel = vmp[BuscarPorRUCViewModel::class.java]
    }

    private fun initListeners() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                dismiss()
            }
            dropdownTipoDoc.setOnItemClickListener { _, _, i, _ ->
                indexTd = i
                clearFields(false)
                edtNumeroDoc.filters = arrayOf(
                    InputFilter.LengthFilter(
                        when (i) {
                            0 -> {
                                btnSearch.text = "Buscar"
                                btnSearch.isEnabled = true
                                changeFieldStatus(false)
                                validated = false
                                8
                            }
                            1 -> {
                                changeFieldStatus(false)
                                btnSearch.text = "Buscar"
                                btnSearch.isEnabled = true
                                validated = false
                                11
                            }
                            else -> {
                                changeFieldStatus(true)
                                btnSearch.text = "No disponible"
                                btnSearch.isEnabled = false
                                20
                            }
                        }
                    )
                )

            }
            btnSearch.setOnClickListener {
                if (indexTd != -1) {
                    if (edtNumeroDoc.text!!.isNotEmpty()) {
                        when (indexTd) {
                            0 -> {
                                if (edtNumeroDoc.text!!.length == 8) {
                                    with(btnSearch) {
                                        text = "Espere un momento . . ."
                                        isEnabled = false
                                    }
                                    AsyncTask.execute {
                                        Thread.sleep(3000)
                                        lifecycleScope.launch {
                                            withContext(Dispatchers.Main) {
                                                searchDNIViewModel.find(edtNumeroDoc.text.toString())
                                                    .observe(viewLifecycleOwner, {
                                                        if (it.success) {
                                                            validated = true
                                                            showData(it.data!!)
                                                        } else {
                                                            Toast.makeText(
                                                                requireContext(),
                                                                it.message,
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                        with(btnSearch) {
                                                            text = "Buscar"
                                                            isEnabled = true
                                                        }
                                                    })
                                            }
                                        }
                                    }
                                } else {
                                    activateError(tiNumeroDoc, "DNI no válido")
                                }
                            }
                            1 -> {
                                if (edtNumeroDoc.text!!.length == 11) {
                                    with(btnSearch) {
                                        text = "Espere un momento . . ."
                                        isEnabled = false
                                    }
                                    AsyncTask.execute {
                                        Thread.sleep(3000)
                                        lifecycleScope.launch {
                                            withContext(Dispatchers.Main) {
                                                searchRUCViewModel.find(edtNumeroDoc.text.toString())
                                                    .observe(viewLifecycleOwner, {
                                                        if (it.success) {
                                                            validated = true
                                                            showData(it.data!!)
                                                        } else {
                                                            Toast.makeText(
                                                                requireContext(),
                                                                it.message,
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                        with(btnSearch) {
                                                            text = "Buscar"
                                                            isEnabled = true
                                                        }
                                                    })
                                            }
                                        }
                                    }

                                } else {
                                    activateError(tiNumeroDoc, "RUC no válido")
                                }
                            }
                        }
                    } else {
                        activateError(tiNumeroDoc)
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "hey,selecciona un tipo de identifiación primero", Toast.LENGTH_SHORT
                    ).show()
                }
            }
            edtNumeroDoc.addTextChangedListener {
                clearFields(true)
                validated = false
                tiNumeroDoc.isErrorEnabled = false
            }
            edtNumeroDoc.setOnEditorActionListener { _, i, _ ->
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard()
                    btnSearch.performClick()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
            btnRegistrarse.setOnClickListener {
                save()
            }
            addTextWatcher(tiNombres, edtNombres)
            addTextWatcher(tiDireccion, edtDireccion)
            addTextWatcher(tiUbigeo, edtUbigeo)
            addTextWatcher(tiTelefono, edtTelefono)
        }
    }

    private fun save() {
        if (indexTd == 2) {
           if(validateOtroDocumento()){
               val c = Cliente()
               with(c) {
                   with(binding) {
                       documento = edtNumeroDoc.text.toString()
                       nombre = edtNombres.text.toString()
                       direccion = edtDireccion.text.toString()
                       ubigeo = edtUbigeo.text.toString()
                       telefono = edtTelefono.text.toString()
                   }
               }
               viewModel.save(c).observe(viewLifecycleOwner, {
                   Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                   if (it.rpta == 1) {
                       dismiss()
                   }
               })
           }
        } else {
            if (validated) {
                if (binding.edtTelefono.text!!.toString().isNotEmpty()) {
                    val c = Cliente()
                    with(c) {
                        with(binding) {
                            documento = edtNumeroDoc.text.toString()
                            nombre = edtNombres.text.toString()
                            direccion = edtDireccion.text.toString()
                            ubigeo = edtUbigeo.text.toString()
                            telefono = edtTelefono.text.toString()
                        }
                    }
                    viewModel.save(c).observe(viewLifecycleOwner, {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        if (it.rpta == 1) {
                            dismiss()
                        }
                    })
                } else {
                    activateError(binding.tiTelefono)
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "debes de validar primero tus datos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun validateOtroDocumento(): Boolean {
        var valid = true
        with(binding) {
            if (edtNumeroDoc.text!!.isEmpty()) {
                valid = false
                activateError(tiNumeroDoc)
            }
            if (edtNombres.text!!.isEmpty()) {
                valid = false
                activateError(tiNombres)
            }
            if (edtDireccion.text!!.isEmpty()) {
                valid = false
                activateError(tiDireccion)
            }
            if (edtUbigeo.text!!.isEmpty()) {
                valid = false
                activateError(tiUbigeo)
            }
            if (edtTelefono.text!!.isEmpty()) {
                valid = false
                activateError(tiTelefono)
            }
        }
        return valid
    }

    private fun addTextWatcher(til: TextInputLayout, edt: EditText) {
        edt.addTextChangedListener {
            til.isErrorEnabled = false
        }
    }

    private fun showData(data: ReniecPerson) {
        with(binding) {
            edtNombres.setText(data.nombre_completo)
            edtDireccion.setText(data.direccion)
            edtUbigeo.setText("${data.ubigeo[0]},${data.ubigeo[1]},${data.ubigeo[2]}")
        }
    }

    private fun showData(data: SunatEmpresa) {
        with(binding) {
            edtNombres.setText(data.nombre_o_razon_social)
            edtDireccion.setText(data.direccion_completa)
            edtUbigeo.setText("${data.ubigeo[0]},${data.ubigeo[1]},${data.ubigeo[2]}")
        }
    }

    private fun activateError(ti: TextInputLayout, errmsg: String = "campo requerido") {
        ti.error = errmsg
    }

    private fun changeFieldStatus(status: Boolean) {
        with(binding) {
            tiNombres.isEnabled = status
            tiDireccion.isEnabled = status
            tiUbigeo.isEnabled = status
        }
    }

    private fun clearFields(ignoreDoc: Boolean) {
        with(binding) {
            if (!ignoreDoc) {
                edtNumeroDoc.text!!.clear()
            }
            edtNombres.text!!.clear()
            edtDireccion.text!!.clear()
            edtUbigeo.text!!.clear()
            edtTelefono.text!!.clear()
        }
        validated = false
    }

    private fun configure() {
        requireDialog().window?.setBackgroundDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.background
            )
        )
        requireDialog().window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        requireDialog().window?.attributes?.windowAnimations = R.style.animation
        binding.dropdownTipoDoc.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                tiposDoc
            )
        )
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = requireActivity().currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
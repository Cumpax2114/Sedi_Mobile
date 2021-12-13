package dev.franklinbg.sedimobile.dialog

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import dev.franklinbg.sedimobile.R
import dev.franklinbg.sedimobile.databinding.DialogRegistrarClienteBinding

class RegistrarClienteDialog : DialogFragment() {
    private val tiposDoc = listOf("DNI", "RUC", "OTRO")
    private var indexTd = -1
    private lateinit var binding: DialogRegistrarClienteBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogRegistrarClienteBinding.inflate(inflater, container, false)
        configure()
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                dismiss()
            }
            dropdownTipoDoc.setOnItemClickListener { _, _, i, _ ->
                indexTd = i
                clearFields()
                edtNumeroDoc.filters = arrayOf(
                    InputFilter.LengthFilter(
                        when (i) {
                            0 -> 8
                            1 -> 11
                            else -> 20
                        }
                    )
                )

            }
        }
    }

    private fun clearFields() {
        with(binding) {
            edtNumeroDoc.text!!.clear()
            edtInputNombres.text!!.clear()
            edtDireccion.text!!.clear()
            edtUbigeo.text!!.clear()
            edtTelefono.text!!.clear()
        }
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
}
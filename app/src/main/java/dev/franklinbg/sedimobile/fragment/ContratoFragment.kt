package dev.franklinbg.sedimobile.fragment

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.franklinbg.sedimobile.databinding.FragmentContratoBinding
import dev.franklinbg.sedimobile.utils.DecimalDigitsInputFilter

class ContratoFragment : Fragment() {
    private lateinit var binding: FragmentContratoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContratoBinding.inflate(inflater, container, false)
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.edtTotal.setFilters(arrayOf(DecimalDigitsInputFilter(2, 2)))
    }
}
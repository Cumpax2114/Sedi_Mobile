package dev.franklinbg.sedimobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.franklinbg.sedimobile.R
import dev.franklinbg.sedimobile.databinding.FragmentContratoBinding

class ContratoFragment : Fragment() {
    private lateinit var binding: FragmentContratoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContratoBinding.inflate(inflater)
        return binding.root
    }
}
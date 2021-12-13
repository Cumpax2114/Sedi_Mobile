package dev.franklinbg.sedimobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.franklinbg.sedimobile.R
import dev.franklinbg.sedimobile.databinding.FragmentClienteBinding
import dev.franklinbg.sedimobile.dialog.RegistrarClienteDialog

class ClienteFragment : Fragment() {
    private lateinit var binding: FragmentClienteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClienteBinding.inflate(inflater)
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.btnNewClient.setOnClickListener {
            RegistrarClienteDialog().show(childFragmentManager, "")
        }
    }
}
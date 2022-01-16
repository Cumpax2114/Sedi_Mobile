package dev.franklinbg.sedimobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.franklinbg.sedimobile.adapter.ProveedorMovCajaAdapter
import dev.franklinbg.sedimobile.communication.SeleccionePersonaDialogCommunication
import dev.franklinbg.sedimobile.databinding.FragmentMovCajaSelectProveedorBinding
import dev.franklinbg.sedimobile.viewmodel.ProveedorViewModel

class MovCajaSelectProveedorFragment(val communication:SeleccionePersonaDialogCommunication) : Fragment() {
    private lateinit var binding: FragmentMovCajaSelectProveedorBinding
    private lateinit var viewModel: ProveedorViewModel
    private lateinit var adapter:ProveedorMovCajaAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovCajaSelectProveedorBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ProveedorViewModel::class.java]
        initAdapter()
        loadData()
        return binding.root
    }
    private fun initAdapter() {
        adapter = ProveedorMovCajaAdapter(communication)
        with(binding.rcvProveedores) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MovCajaSelectProveedorFragment.adapter
        }
    }

    private fun loadData() {
        viewModel.listActivos().observe(viewLifecycleOwner) {
            if (it.rpta == 1) {
                adapter.updateItems(it.body!!)
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
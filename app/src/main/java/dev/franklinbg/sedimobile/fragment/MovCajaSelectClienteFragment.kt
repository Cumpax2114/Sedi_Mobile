package dev.franklinbg.sedimobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.franklinbg.sedimobile.adapter.ClienteMovCajaAdapter
import dev.franklinbg.sedimobile.communication.SeleccionePersonaDialogCommunication
import dev.franklinbg.sedimobile.databinding.FragmentMovCajaSelectClienteBinding
import dev.franklinbg.sedimobile.viewmodel.ClienteViewModel

class MovCajaSelectClienteFragment(val communication:SeleccionePersonaDialogCommunication) : Fragment() {
    private lateinit var binding: FragmentMovCajaSelectClienteBinding
    private lateinit var adapter: ClienteMovCajaAdapter
    private lateinit var viewModel: ClienteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovCajaSelectClienteBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ClienteViewModel::class.java]
        initAdapter()
        loadData()
        return binding.root
    }

    private fun initAdapter() {
        adapter = ClienteMovCajaAdapter(communication)
        with(binding.rcvCientes) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MovCajaSelectClienteFragment.adapter
        }
    }

    private fun loadData() {
        viewModel.listAll().observe(viewLifecycleOwner) {
            if (it.rpta == 1) {
                adapter.updateItems(it.body!!)
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
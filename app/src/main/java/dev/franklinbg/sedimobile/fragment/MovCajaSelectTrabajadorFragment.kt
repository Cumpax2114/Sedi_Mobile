package dev.franklinbg.sedimobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.franklinbg.sedimobile.adapter.TrabajadorMovCajaAdapter
import dev.franklinbg.sedimobile.communication.SeleccionePersonaDialogCommunication
import dev.franklinbg.sedimobile.databinding.FragmentMovCajaSelectTrabajadorBinding
import dev.franklinbg.sedimobile.utils.UsuarioContainer
import dev.franklinbg.sedimobile.viewmodel.UsuarioViewModel

class MovCajaSelectTrabajadorFragment(val communication: SeleccionePersonaDialogCommunication) :
    Fragment() {
    private lateinit var binding: FragmentMovCajaSelectTrabajadorBinding
    private lateinit var adapter: TrabajadorMovCajaAdapter
    private lateinit var viewModel: UsuarioViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovCajaSelectTrabajadorBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UsuarioViewModel::class.java]
        initAdapter()
        loadData()
        return binding.root
    }

    private fun initAdapter() {
        adapter = TrabajadorMovCajaAdapter(communication)
        with(binding.rcvTrabajadores) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MovCajaSelectTrabajadorFragment.adapter
        }
    }

    private fun loadData() {
        if (UsuarioContainer.currentUser != null) {
            viewModel.listForMovCaja(UsuarioContainer.currentUser!!.id)
                .observe(viewLifecycleOwner) {
                    if (it.rpta == 1) {
                        adapter.updateItems(it.body!!)
                    } else {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
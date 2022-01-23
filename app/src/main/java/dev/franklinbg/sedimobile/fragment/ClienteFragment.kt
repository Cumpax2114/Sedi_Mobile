package dev.franklinbg.sedimobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.franklinbg.sedimobile.R
import dev.franklinbg.sedimobile.activity.HomeActivity
import dev.franklinbg.sedimobile.adapter.ClienteAdapter
import dev.franklinbg.sedimobile.databinding.FragmentClienteBinding
import dev.franklinbg.sedimobile.dialog.RegistrarClienteDialog
import dev.franklinbg.sedimobile.viewmodel.ClienteViewModel

class ClienteFragment : Fragment() {
    private lateinit var binding: FragmentClienteBinding
    private lateinit var adapter: ClienteAdapter
    private lateinit var viewModel: ClienteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClienteBinding.inflate(inflater)
        initViewModel()
        initAdapter()
        loadData()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as HomeActivity).setMenuToolbar(R.menu.menu_clientes)
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as HomeActivity).clearMenu()
    }

    private fun loadData() {
        viewModel.listAll().observe(viewLifecycleOwner, {
            if (it.rpta == 1) {
                adapter.updateItems(it.body!!)
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initAdapter() {
        adapter = ClienteAdapter()
        with(binding.rcvClientes) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ClienteFragment.adapter
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ClienteViewModel::class.java]
    }

}
package dev.franklinbg.sedimobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import dev.franklinbg.sedimobile.R
import dev.franklinbg.sedimobile.adapter.CajaFragmentAdapter
import dev.franklinbg.sedimobile.databinding.FragmentCajaBinding
import dev.franklinbg.sedimobile.utils.SeccionCaja

class CajaFragment : Fragment() {
    private lateinit var binding: FragmentCajaBinding
    private lateinit var adapter: CajaFragmentAdapter
    private val secciones = listOf(
        SeccionCaja(1, "Caja chica", R.drawable.gastos),
        SeccionCaja(2, "Movimiento de caja", R.drawable.cajaregistradora),
        SeccionCaja(3, "Reportes", R.drawable.reporte)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCajaBinding.inflate(inflater)
        loadSections()
        return binding.root
    }

    private fun loadSections() {
        adapter = CajaFragmentAdapter(secciones)
        with(binding.rcvSecciones) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@CajaFragment.adapter
        }
    }
}
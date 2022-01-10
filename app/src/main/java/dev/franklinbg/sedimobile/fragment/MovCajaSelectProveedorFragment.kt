package dev.franklinbg.sedimobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.franklinbg.sedimobile.R
import dev.franklinbg.sedimobile.databinding.FragmentMovCajaSelectProveedorBinding

class MovCajaSelectProveedorFragment : Fragment() {
    private lateinit var binding: FragmentMovCajaSelectProveedorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovCajaSelectProveedorBinding.inflate(inflater, container, false)
        return binding.root
    }
}
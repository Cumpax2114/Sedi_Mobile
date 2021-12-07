package dev.franklinbg.sedimobile.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.franklinbg.sedimobile.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val url =
        "https://www.google.com/maps/place/Pedro+Ruiz+959,+Chiclayo+14001/@-6.7674917,-79.8391426,19z/data=!4m5!3m4!1s0x904ceed71aa656c1:0x61a42c664a677134!8m2!3d-6.7677567!4d-79.8387644"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        loadMap()
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadMap() {
        with(binding) {
            val settings = wbMap.settings
            settings.javaScriptEnabled = true
            wbMap.loadUrl(url)
        }
    }
}
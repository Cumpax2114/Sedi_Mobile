package dev.franklinbg.sedimobile.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import dev.franklinbg.sedimobile.R
import dev.franklinbg.sedimobile.adapter.ViewPagerAdapter
import dev.franklinbg.sedimobile.databinding.DialogSeleccionePersonaBinding

class SeleccionePersonaDialog : DialogFragment() {
    private lateinit var binding: DialogSeleccionePersonaBinding
    private val fragmentsLabels = arrayOf("Cliente", "Trabajador", "Empleado")
    private lateinit var adapter: ViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        configure()
        binding = DialogSeleccionePersonaBinding.inflate(inflater, container, false)
        adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, i ->
            tab.text = fragmentsLabels[i]
        }.attach()
        return binding.root
    }

    private fun configure() {
        requireDialog().window?.setBackgroundDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.background
            )
        )
        requireDialog().window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        requireDialog().window?.attributes?.windowAnimations = R.style.animation
    }
}
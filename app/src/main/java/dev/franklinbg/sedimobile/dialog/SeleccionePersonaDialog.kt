package dev.franklinbg.sedimobile.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import dev.franklinbg.sedimobile.R
import dev.franklinbg.sedimobile.activity.MovimientoCajaActivity
import dev.franklinbg.sedimobile.adapter.ViewPagerAdapter
import dev.franklinbg.sedimobile.communication.SeleccionePersonaDialogCommunication
import dev.franklinbg.sedimobile.databinding.DialogSeleccionePersonaBinding
import dev.franklinbg.sedimobile.model.Cliente
import dev.franklinbg.sedimobile.model.Proveedor
import dev.franklinbg.sedimobile.model.Usuario
import dev.franklinbg.sedimobile.utils.PersonMovCajaContainer

class SeleccionePersonaDialog : DialogFragment(), SeleccionePersonaDialogCommunication {
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
        initAdapter()
        configureTabLayout()
        binding.toolbar.setNavigationOnClickListener {
            PersonMovCajaContainer.clear()
            dismiss()
        }
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

    private fun initAdapter() {
        adapter = ViewPagerAdapter(childFragmentManager, lifecycle, this)
        binding.viewPager.adapter = adapter
    }

    private fun configureTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, i ->
            tab.text = fragmentsLabels[i]
        }.attach()
    }

    override fun addCliente(cliente: Cliente) {
        PersonMovCajaContainer.cliente = cliente
        PersonMovCajaContainer.type = 1
        dismiss()
    }

    override fun addUsuario(usuario: Usuario) {
        PersonMovCajaContainer.usuario = usuario
        PersonMovCajaContainer.type = 2
        dismiss()
    }

    override fun appProveedor(proveedor: Proveedor) {
        PersonMovCajaContainer.proveedor = proveedor
        PersonMovCajaContainer.type = 3
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        (requireActivity() as MovimientoCajaActivity).presentPerson()
    }
}
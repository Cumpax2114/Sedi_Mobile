package dev.franklinbg.sedimobile.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.franklinbg.sedimobile.fragment.MovCajaSelectClienteFragment
import dev.franklinbg.sedimobile.fragment.MovCajaSelectProveedorFragment
import dev.franklinbg.sedimobile.fragment.MovCajaSelectTrabajadorFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifeCycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifeCycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MovCajaSelectClienteFragment()
            1 -> MovCajaSelectTrabajadorFragment()
            else -> MovCajaSelectProveedorFragment()
        }
    }
}
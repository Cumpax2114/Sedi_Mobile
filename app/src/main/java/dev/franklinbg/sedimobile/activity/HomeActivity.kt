package dev.franklinbg.sedimobile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.navigation.NavigationView
import dev.franklinbg.sedimobile.R
import dev.franklinbg.sedimobile.databinding.ActivityHomeBinding
import dev.franklinbg.sedimobile.dialog.RegistrarClienteDialog
import dev.franklinbg.sedimobile.utils.UsuarioContainer

class HomeActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_caja, R.id.nav_cliente, R.id.nav_contrato, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        loadUserData()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun loadUserData() {
        val headerView = binding.navView.getHeaderView(0)

        if (UsuarioContainer.currentUser != null) {
            val user = UsuarioContainer.currentUser!!
            headerView.findViewById<TextView>(R.id.tvNombreUsuario).text =
                "¡Hola, ${user.nombre}"
            headerView.findViewById<TextView>(R.id.tvCorreoUsuario).text = user.correo
        }
    }

    override fun onBackPressed() {
        SweetAlertDialog(
            this,
            SweetAlertDialog.WARNING_TYPE
        ).setTitleText("Has oprimido el botón atrás")
            .setContentText("¿Quieres cerrar sesión?")
            .setCancelText("No, Cancelar!").setConfirmText("Sí, Cerrar")
            .showCancelButton(true).setCancelClickListener { sDialog: SweetAlertDialog ->
                sDialog.dismissWithAnimation()
                SweetAlertDialog(
                    this,
                    SweetAlertDialog.ERROR_TYPE
                ).setTitleText("Operación cancelada")
                    .setContentText("No has cerrado sesión")
                    .show()
            }.setConfirmClickListener { sweetAlertDialog: SweetAlertDialog ->
                UsuarioContainer.currentUser = null
                sweetAlertDialog.dismissWithAnimation()
                finish()
            }.show()
    }

    fun setMenuToolbar(menuId: Int) {
        binding.appBarMain.toolbar.inflateMenu(menuId)
        binding.appBarMain.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.itemNewClient -> {
                    RegistrarClienteDialog().show(supportFragmentManager, "")
                    return@setOnMenuItemClickListener true
                }
            }
            return@setOnMenuItemClickListener false
        }
    }

    fun clearMenu() = binding.appBarMain.toolbar.menu.clear()
}
package dev.franklinbg.sedimobile.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import dev.franklinbg.sedimobile.databinding.ActivityLoginBinding
import dev.franklinbg.sedimobile.utils.UsuarioContainer
import dev.franklinbg.sedimobile.utils.activateTextInputError
import dev.franklinbg.sedimobile.utils.hayRed
import dev.franklinbg.sedimobile.viewmodel.UsuarioViewModel
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: UsuarioViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initListeners()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[UsuarioViewModel::class.java]
    }

    private fun initListeners() {
        with(binding) {
            btnIniciarSesion.setOnClickListener {
                if (hayRed(this@LoginActivity)) {
                    if (validate()) {
                        with(btnIniciarSesion) {
                            text = "Espere un momento . . ."
                            isEnabled = false
                        }
                        tiEmail.isEnabled = false
                        tiPassword.isEnabled = false
                        Handler(Looper.getMainLooper()).postDelayed({
                            viewModel.login(edtEmail.text.toString(), edtPassword.text.toString())
                                .observe(this@LoginActivity) {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        it.message,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    if (it.rpta == 1) {
                                        UsuarioContainer.currentUser = it.body
                                        startActivity(
                                            Intent(
                                                this@LoginActivity,
                                                HomeActivity::class.java
                                            )
                                        )
                                    } else {
                                        Toast.makeText(
                                            this@LoginActivity,
                                            it.message,
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                    with(btnIniciarSesion) {
                                        text = "Iniciar Sesión"
                                        isEnabled = true
                                    }
                                    tiEmail.isEnabled = true
                                    tiPassword.isEnabled = true
                                }
                        }, 3000)
                    }
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "No tienes conexión a internet",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            addTextWatcher(edtEmail, tiEmail)
            addTextWatcher(edtPassword, tiPassword)
            edtPassword.setOnEditorActionListener { _, i, _ ->
                if (i == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard()
                    btnIniciarSesion.performClick()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
    }

    private fun validate(): Boolean {
        var valid = true
        with(binding) {
            if (edtEmail.text!!.isEmpty()) {
                valid = false
                activateTextInputError(tiEmail)
            } else if (isNotValidMail(edtEmail.text!!.toString())) {
                valid = false
                activateTextInputError(tiEmail, "ingrese un correo válido")
            }
            if (edtPassword.text!!.isEmpty()) {
                valid = false
                activateTextInputError(tiPassword)
            }
        }
        return valid
    }

    private fun isNotValidMail(email: String): Boolean {
        val regexPattern = "^(.+)@(\\S+)$"
        return !Pattern.compile(regexPattern).matcher(email).matches()
    }

    private fun addTextWatcher(edt: EditText, til: TextInputLayout) {
        edt.addTextChangedListener {
            til.isErrorEnabled = false
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
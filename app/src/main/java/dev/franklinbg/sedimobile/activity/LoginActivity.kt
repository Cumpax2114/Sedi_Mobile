package dev.franklinbg.sedimobile.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import dev.franklinbg.sedimobile.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            btnIniciarSesion.setOnClickListener {
                if (validate()) {
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                }
            }
            addTextWatcher(edtEmail, tiEmail)
            addTextWatcher(edtPassword, tiPassword)
        }
    }

    private fun validate(): Boolean {
        var valid = true
        with(binding) {
            if (edtEmail.text!!.isEmpty()) {
                valid = false
                activateError(tiEmail)
            } else if (isNotValidMail(edtEmail.text!!.toString())) {
                valid = false
                activateError(tiEmail, "ingrese un correo válido")
            }
            if (edtPassword.text!!.isEmpty()) {
                valid=false
                activateError(tiPassword)
            }
        }
        return valid
    }

    private fun isNotValidMail(email: String): Boolean {
        val regexPattern = "^(.+)@(\\S+)$"
        return !Pattern.compile(regexPattern).matcher(email).matches()
    }

    private fun activateError(til: TextInputLayout, error: String = "campo abligatorio") {
        til.error = error
    }

    private fun addTextWatcher(edt: EditText, til: TextInputLayout) {
        edt.addTextChangedListener {
            til.isErrorEnabled = false
        }
    }
}
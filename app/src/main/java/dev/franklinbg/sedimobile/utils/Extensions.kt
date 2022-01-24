package dev.franklinbg.sedimobile.utils

import com.google.android.material.textfield.TextInputLayout

fun activateTextInputError(til: TextInputLayout, error: String = "campo abligatorio") {
    til.error = error
}
package dev.franklinbg.sedimobile.utils

class GenericResponse<T>(
    var type: String = "",
    var rpta: Int = 0,
    var message: String = "",
    var body: T? = null
)
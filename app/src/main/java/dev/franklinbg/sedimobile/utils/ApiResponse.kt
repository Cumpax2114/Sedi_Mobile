package dev.franklinbg.sedimobile.utils

class ApiResponse<T>(
    var success: Boolean = false,
    var message: String = "- - -",
    var data: T? = null
)
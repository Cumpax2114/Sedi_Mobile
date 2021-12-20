package dev.franklinbg.sedimobile.communication

import dev.franklinbg.sedimobile.model.DetalleCaja

interface AddDetailCommunication {
    fun addDetail(detail: DetalleCaja):String
}
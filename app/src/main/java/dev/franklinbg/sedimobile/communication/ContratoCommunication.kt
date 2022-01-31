package dev.franklinbg.sedimobile.communication

import dev.franklinbg.sedimobile.model.Contrato

interface ContratoCommunication {
    fun pagar(contrato: Contrato)
}
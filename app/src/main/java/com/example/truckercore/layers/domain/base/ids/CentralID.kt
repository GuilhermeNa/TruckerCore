package com.example.truckercore.layers.domain.base.ids

import com.example.truckercore.layers.domain.base.contracts.ID

@JvmInline
value class CentralID(override val value: String): ID {

    init {
        validate()
    }

    companion object {
        fun create() = CompanyID(ID.generateUUID())
    }

}
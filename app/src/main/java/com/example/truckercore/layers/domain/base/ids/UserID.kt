package com.example.truckercore.layers.domain.base.ids

import com.example.truckercore.layers.domain.base.contracts.ID

@JvmInline
value class UserID(override val value: String) : ID {

    init {
        validate()
    }

    companion object {
        fun generate() = UserID(ID.generateUUID())
    }

}
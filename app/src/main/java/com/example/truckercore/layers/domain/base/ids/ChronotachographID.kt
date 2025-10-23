package com.example.truckercore.layers.domain.base.ids

import com.example.truckercore.layers.domain.base.contracts.entity.ID

@JvmInline
value class ChronotachographID(override val value: String): ID {

    init {
        validate()
    }

    companion object {
        fun create() = ChronotachographID(ID.generateRandomUUID())
    }

}
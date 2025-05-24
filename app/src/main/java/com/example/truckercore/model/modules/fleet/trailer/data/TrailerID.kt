package com.example.truckercore.model.modules.fleet.trailer.data

import com.example.truckercore.model.modules._shared.contracts.entity.ID

@JvmInline
value class TrailerID(override val value: String) : ID {

    init {
        validate()
    }

    companion object {
        fun generate() = TrailerID(ID.generate())
    }

}
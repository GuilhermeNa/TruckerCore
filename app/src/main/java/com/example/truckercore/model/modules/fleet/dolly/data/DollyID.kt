package com.example.truckercore.model.modules.fleet.dolly.data

import com.example.truckercore.model.modules._shared.contracts.entity.ID

@JvmInline
value class DollyID(override val value: String): ID {

    init {
        validate()
    }

    companion object {
        fun generate() = DollyID(ID.generate())
    }

}
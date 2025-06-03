package com.example.truckercore.model.modules.fleet.truck.data

import com.example.truckercore.model.modules._shared._contracts.entity.ID

@JvmInline
value class TruckID(override val value: String) : ID {

    init {
        validate()
    }

    companion object {
        fun generate() = TruckID(ID.generate())
    }

}
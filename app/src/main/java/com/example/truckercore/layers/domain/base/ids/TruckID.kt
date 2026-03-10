package com.example.truckercore.layers.domain.base.ids

import com.example.truckercore.layers.domain.base.contracts.ID
import com.example.truckercore.layers.domain.base.contracts.VehicleID

@JvmInline
value class TruckID(override val value: String) : VehicleID {

    init {
        validate()
    }

    companion object {
        fun create() = TruckID(ID.generateUUID())
    }

}
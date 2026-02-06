package com.example.truckercore.layers.domain.base.ids

import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.contracts.entity.VehicleID

@JvmInline
value class TruckID(override val value: String) : VehicleID {

    init {
        validate()
    }

    companion object {
        fun create() = TruckID(ID.generateUUID())
    }

}
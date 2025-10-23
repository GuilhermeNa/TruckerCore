package com.example.truckercore.layers.domain.base.ids

import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.contracts.others.TransportItemID

@JvmInline
value class TruckID(override val value: String) : TransportItemID {

    init {
        validate()
    }

    companion object {
        fun generate() = TruckID(ID.generateRandomUUID())
    }

}
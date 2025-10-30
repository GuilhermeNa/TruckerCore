package com.example.truckercore.layers.domain.base.ids

import com.example.truckercore.layers.domain.base.contracts.entity.ID

@JvmInline
value class TrailerID(override val value: String) : VehicleID {

    init {
        validate()
    }

    companion object {
        fun create() = TrailerID(ID.generateRandomUUID())
    }

}
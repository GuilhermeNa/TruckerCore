package com.example.truckercore.layers.domain.base.ids

import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.contracts.others.TransportItemID

@JvmInline
value class TrailerID(override val value: String): TransportItemID {

    init {
        validate()
    }

    companion object {
        fun create() = TrailerID(ID.generateRandomUUID())
    }

}
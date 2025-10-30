package com.example.truckercore.layers.domain.base.ids

import com.example.truckercore.layers.domain.base.contracts.entity.ID

@JvmInline
value class HitchID(override val value: String) : ID {

    init {
        validate()
    }

    companion object {
        fun create() = HitchID(ID.generateRandomUUID())
    }



}
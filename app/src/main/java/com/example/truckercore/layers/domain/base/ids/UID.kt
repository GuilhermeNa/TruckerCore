package com.example.truckercore.layers.domain.base.ids

import com.example.truckercore.layers.domain.base.contracts.ID

@JvmInline
value class UID(override val value: String) : ID {

    init {
        validate()
    }

}
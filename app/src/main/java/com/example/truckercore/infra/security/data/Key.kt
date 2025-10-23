package com.example.truckercore.infra.security.data

import com.example.truckercore.layers.domain.base.contracts.entity.ID

@JvmInline
value class Key(override val value: String):
    ID {
    init {
        validate()
    }
}

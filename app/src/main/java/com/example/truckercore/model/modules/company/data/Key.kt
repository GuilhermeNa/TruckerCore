package com.example.truckercore.model.modules.company.data

import com.example.truckercore.model.modules._contracts.ID

@JvmInline
value class Key(override val value: String): ID {
    init {
        validate()
    }
}

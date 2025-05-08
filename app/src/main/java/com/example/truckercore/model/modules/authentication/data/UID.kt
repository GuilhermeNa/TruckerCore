package com.example.truckercore.model.modules.authentication.data

import com.example.truckercore.model.modules._contracts.ID

@JvmInline
value class UID(override val value: String) : ID {

    init {
        validate()
    }

}
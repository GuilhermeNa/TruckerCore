package com.example.truckercore.model.modules.user.data

import com.example.truckercore.model.modules._contracts.ID

@JvmInline
value class UserID(override val value: String) : ID {

    init {
        validate()
    }

}
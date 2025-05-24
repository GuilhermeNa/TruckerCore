package com.example.truckercore.model.modules.user.data

import com.example.truckercore.model.modules._shared.contracts.entity.ID

@JvmInline
value class UserID(override val value: String) : ID {

    init {
        validate()
    }

    companion object {
        fun generate() = UserID(ID.generate())
    }

}
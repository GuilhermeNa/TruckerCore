package com.example.truckercore.model.modules.user.data_helper

import com.example.truckercore.model.modules.user.exceptions.InvalidUserIdException
import com.example.truckercore.model.shared.interfaces.data.ID

@JvmInline
value class UserID(override val value: String) : ID {

    init {
        if (value.isBlank()) {
            throw InvalidUserIdException("Invalid UserID: ID must be a non-blank string.")
        }
    }

}
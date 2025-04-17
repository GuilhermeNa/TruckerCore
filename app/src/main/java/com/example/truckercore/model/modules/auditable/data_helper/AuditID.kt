package com.example.truckercore.model.modules.auditable.data_helper

import com.example.truckercore.model.modules.auditable.exceptions.InvalidAuditIdException
import com.example.truckercore.model.shared.interfaces.data.ID

@JvmInline
value class AuditID(override val value: String) : ID {

    init {
        if (value.isBlank()) {
            throw InvalidAuditIdException("Invalid AuditId: ID must be a non-blank string.")
        }
    }

}
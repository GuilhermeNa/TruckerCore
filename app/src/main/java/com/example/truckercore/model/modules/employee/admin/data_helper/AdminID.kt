package com.example.truckercore.model.modules.employee.admin.data_helper

import com.example.truckercore.model.modules.employee.admin.exceptions.InvalidAdminIdException
import com.example.truckercore.model.shared.interfaces.data.ID

@JvmInline
value class AdminID(override val value: String): ID {

    init {
        if (value.isBlank()) {
            throw InvalidAdminIdException("Invalid AdminID: ID must be a non-blank string.")
        }
    }

}
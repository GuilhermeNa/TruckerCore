package com.example.truckercore.model.modules.company.data_helper

import com.example.truckercore.model.modules.company.exceptions.InvalidCompanyIdException
import com.example.truckercore.model.shared.interfaces.data.ID

@JvmInline
value class CompanyID(override val value: String): ID {

    init {
        if (value.isBlank()) {
            throw InvalidCompanyIdException("Invalid CompanyId: ID must be a non-blank string.")
        }
    }
}
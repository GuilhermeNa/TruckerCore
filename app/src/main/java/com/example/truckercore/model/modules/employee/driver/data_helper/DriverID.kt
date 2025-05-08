package com.example.truckercore.model.modules.employee.driver.data_helper

import com.example.truckercore.model.modules.employee.driver.exceptions.InvalidDriverIdException
import com.example.truckercore.model.modules._contracts.ID

@JvmInline
value class DriverID(override val value: String): ID {

    init {
        if (value.isBlank()) {
            throw InvalidDriverIdException("Invalid DriverID: ID must be a non-blank string.")
        }
    }

}
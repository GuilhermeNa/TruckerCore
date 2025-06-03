package com.example.truckercore.model.modules.employee.driver.data

import com.example.truckercore.model.modules._shared._contracts.entity.ID

@JvmInline
value class DriverID(override val value: String): ID {

    init {
        validate()
    }

    companion object {
        fun generate() = DriverID(ID.generate())
    }

}
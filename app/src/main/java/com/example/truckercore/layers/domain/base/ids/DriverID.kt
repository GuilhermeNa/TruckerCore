package com.example.truckercore.layers.domain.base.ids

import com.example.truckercore.layers.domain.base.contracts.EmployeeID
import com.example.truckercore.layers.domain.base.contracts.ID

@JvmInline
value class DriverID(override val value: String) : EmployeeID {

    init {
        validate()
    }

    companion object {
        fun create() = DriverID(ID.generateUUID())
    }

}
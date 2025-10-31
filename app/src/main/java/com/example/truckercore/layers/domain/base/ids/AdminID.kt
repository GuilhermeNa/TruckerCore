package com.example.truckercore.layers.domain.base.ids

import com.example.truckercore.layers.domain.base.contracts.entity.EmployeeID
import com.example.truckercore.layers.domain.base.contracts.entity.ID

@JvmInline
value class AdminID(override val value: String) : EmployeeID {

    init {
        validate()
    }

    companion object {
        fun create() = AdminID(ID.generateRandomUUID())
    }

}
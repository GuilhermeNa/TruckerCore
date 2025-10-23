package com.example.truckercore.layers.domain.model.employee.admin.data

import com.example.truckercore.layers.domain.base.contracts.entity.ID

@JvmInline
value class AdminID(override val value: String): ID {

    init {
        validate()
    }

    companion object {
        fun generate() = AdminID(ID.generateRandomUUID())
    }

}
package com.example.truckercore.model.modules.employee.admin.data

import com.example.truckercore.model.modules._shared._contracts.entity.ID

@JvmInline
value class AdminID(override val value: String): ID {

    init {
        validate()
    }

    companion object {
        fun generate() = AdminID(ID.generate())
    }

}
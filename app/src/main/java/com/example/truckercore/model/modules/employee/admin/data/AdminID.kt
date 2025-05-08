package com.example.truckercore.model.modules.employee.admin.data

import com.example.truckercore.model.modules._contracts.ID

@JvmInline
value class AdminID(override val value: String): ID {

    init {
        validate()
    }

}
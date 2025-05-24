package com.example.truckercore.model.modules.employee.autonomous.data

import com.example.truckercore.model.modules._shared.contracts.entity.ID

@JvmInline
value class AutID(override val value: String) : ID {

    init {
        validate()
    }

    companion object {
        fun generate() = AutID(ID.generate())
    }

}
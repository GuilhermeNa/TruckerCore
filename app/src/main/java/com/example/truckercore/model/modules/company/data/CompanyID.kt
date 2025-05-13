package com.example.truckercore.model.modules.company.data

import com.example.truckercore.model.modules._contracts.ID

@JvmInline
value class CompanyID(override val value: String) : ID {

    init {
        validate()
    }

    companion object {
        fun generate() = CompanyID(ID.generate())
    }

}
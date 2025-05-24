package com.example.truckercore.model.modules.fleet.dump.data

import com.example.truckercore.model.modules._shared.contracts.entity.ID

@JvmInline
value class DumpID(override val value: String) : ID {

    init {
        validate()
    }

    companion object {
        fun generate() = DumpID(ID.generate())
    }

}
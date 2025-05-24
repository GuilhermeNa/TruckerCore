package com.example.truckercore.model.modules.fleet.dry_van.data

import com.example.truckercore.model.modules._shared.contracts.entity.ID

@JvmInline
value class DryVanID(override val value: String): ID {

    init {
        validate()
    }

    companion object {
        fun generate() = DryVanID(ID.generate())
    }

}
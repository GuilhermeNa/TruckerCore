package com.example.truckercore.model.modules.aggregation.transport_unit.data

import com.example.truckercore.model.modules._shared.contracts.entity.ID

@JvmInline
value class TransportUnitID(override val value: String) : ID {

    init {
        validate()
    }

}
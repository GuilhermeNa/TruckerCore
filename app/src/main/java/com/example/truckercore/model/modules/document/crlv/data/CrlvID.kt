package com.example.truckercore.model.modules.document.crlv.data

import com.example.truckercore.model.modules._shared.contracts.entity.ID

@JvmInline
value class CrlvID(override val value: String) : ID {

    init {
        validate()
    }

}
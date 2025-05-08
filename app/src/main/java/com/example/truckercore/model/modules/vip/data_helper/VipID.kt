package com.example.truckercore.model.modules.vip.data_helper

import com.example.truckercore.model.modules.vip.exceptions.InvalidVipIdException
import com.example.truckercore.model.modules._contracts.ID

@JvmInline
value class VipID(override val value: String): ID {

    init {
        if (value.isBlank()) {
            throw InvalidVipIdException("Invalid VipId: ID must be a non-blank string.")
        }
    }

}
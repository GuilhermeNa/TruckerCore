package com.example.truckercore.layers.data.base.dto.contracts

import com.example.truckercore.layers.domain.base.enums.Status

interface BaseDto {
    val id: String?
    val persistenceState: Status?
}
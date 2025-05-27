package com.example.truckercore.model.infrastructure.integration.data.for_app.contracts

import com.example.truckercore.model.modules._shared.enums.PersistenceState

interface BaseDto {
    val id: String?
    val persistenceState: PersistenceState?
}
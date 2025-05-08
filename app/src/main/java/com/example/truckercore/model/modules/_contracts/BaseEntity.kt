package com.example.truckercore.model.modules._contracts

import com.example.truckercore.model.shared.enums.Persistence

interface BaseEntity {
    val id: ID
    val persistence: Persistence
}
package com.example.truckercore.model.shared.interfaces.data.entity

import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.shared.interfaces.data.ID

interface BaseEntity {
    val id: ID
    val persistence: Persistence
}
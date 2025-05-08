package com.example.truckercore.model.infrastructure.integration.data.for_app.contracts

import com.example.truckercore.model.shared.enums.Persistence

interface BaseDto {
    val id: String?
    val persistence: Persistence?

    fun copyWith(id: String? = this.id): BaseDto

}
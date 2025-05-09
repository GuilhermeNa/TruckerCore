package com.example.truckercore.model.modules._contracts

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto

interface Mapper<E : BaseEntity, D : BaseDto> {

    fun toDto(entity: E): D

    fun toEntity(dto: D): E

}
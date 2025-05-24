package com.example.truckercore.model.modules._shared.contracts.mapper

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.modules._shared.contracts.entity.BaseEntity

interface Mapper<E : BaseEntity<E>, D : BaseDto> : MapperErrorHandler {

    fun toDto(entity: E): D

    fun toEntity(dto: D): E

}


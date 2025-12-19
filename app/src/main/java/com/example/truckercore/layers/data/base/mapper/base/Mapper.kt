package com.example.truckercore.layers.data.base.mapper.base

import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.domain.base.contracts.entity.BaseEntity

interface Mapper<D : BaseDto, E : BaseEntity> {

    fun toDto(entity: E): D

    fun toEntity(dto: D): E

    fun toDtos(entities: List<E>): List<D>

    fun toEntities(dtos: List<D>): List<E>

}


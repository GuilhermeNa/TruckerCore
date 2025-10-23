package com.example.truckercore.layers.data.repository.data.expressions

import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.mapper.base.MapperResolver
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.specification._contracts.Specification
import com.example.truckercore.layers.domain.base.contracts.entity.BaseEntity

fun <D : BaseDto, E : BaseEntity> D?.getResponse(
    spec: Specification<D>
) = if (this == null) {
    DataOutcome.Empty
} else {
    val data = MapperResolver<D, E>(spec.dtoClass).toEntity(this)
    DataOutcome.Success(data)
}

fun <D : BaseDto, E : BaseEntity> List<D>?.getResponse(
    spec: Specification<D>
) = if (this == null) {
    DataOutcome.Empty
} else {
    val data = MapperResolver<D, E>(spec.dtoClass).toEntityList(this)
    DataOutcome.Success(data)
}
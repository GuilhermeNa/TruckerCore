package com.example.truckercore.layers.data.base.mapper.base

import com.example.truckercore.core.error.DataException
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.dto.impl.CompanyDto
import com.example.truckercore.layers.data.base.dto.impl.UserDto
import com.example.truckercore.layers.data.base.mapper.impl.CompanyMapper
import com.example.truckercore.layers.data.base.mapper.impl.UserMapper
import com.example.truckercore.layers.domain.base.contracts.entity.BaseEntity

object MapperResolver {

    @Suppress("UNCHECKED_CAST")
    operator fun <D : BaseDto, E : BaseEntity> invoke(clazz: Class<D>): Mapper<D, E> {
        return when (clazz) {
            CompanyDto::class.java -> CompanyMapper as Mapper<D, E>
            UserDto::class.java -> UserMapper as Mapper<D, E>
            else -> throw DataException.Mapping(
                "Mapper not found for DTO: ${clazz::class.simpleName}"
            )
        }
    }

}
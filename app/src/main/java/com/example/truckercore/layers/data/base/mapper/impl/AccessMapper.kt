package com.example.truckercore.layers.data.base.mapper.impl

import com.example.truckercore.core.error.DataException
import com.example.truckercore.layers.data.base.dto.impl.AccessDto
import com.example.truckercore.layers.data.base.mapper.base.Mapper
import com.example.truckercore.layers.domain.base.ids.AccessID
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.access.Access

object AccessMapper : Mapper<AccessDto, Access> {

    override fun toDto(entity: Access): AccessDto = try {
        with(entity) {
            AccessDto(
                id = idValue,
                status = status,
                companyId = idValue,
                authorized = authorized,
                userId = userId.value,
                role = role
            )
        }
    } catch (e: Exception) {
        throw DataException.Mapping("Error while mapping an Access to dto: $entity")
    }

    override fun toEntity(dto: AccessDto): Access = try {
        with(dto) {
            Access(
                id = AccessID(id!!),
                status = status!!,
                companyId = CompanyID(companyId!!),
                authorized = authorized!!,
                userId = UserID(userId!!),
                role = role!!
            )
        }
    } catch (e: Exception) {
        throw DataException.Mapping("Error while mapping an AccessDto to entity: $dto")
    }

    override fun toDtos(entities: List<Access>): List<AccessDto> =
        entities.map { toDto(it) }

    override fun toEntities(dtos: List<AccessDto>): List<Access> =
        dtos.map { toEntity(it) }

}
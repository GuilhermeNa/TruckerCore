package com.example.truckercore.layers.data.base.mapper.impl

import com.example.truckercore.core.error.DataException
import com.example.truckercore.layers.data.base.dto.impl.UserDto
import com.example.truckercore.layers.data.base.mapper.base.Mapper
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.user.UserDraft

object UserMapper : Mapper<UserDto, UserDraft> {

    override fun toDto(entity: UserDraft): UserDto = try {
        with(entity) {
            UserDto(
                uid = uid.value,
                id = idValue,
                companyId = companyId.value,
                status = status
            )
        }
    } catch (e: Exception) {
        throw DataException.Mapping("Error while mapping an UserDraft to dto: $entity")
    }

    override fun toEntity(dto: UserDto): UserDraft = try {
        with(dto) {
            UserDraft(
                uid = UID(uid!!),
                id = UserID(id!!),
                companyId = CompanyID(companyId!!),
                status = status!!
            )
        }
    } catch (e: Exception) {
        throw DataException.Mapping("Error while mapping an UserDto to entity: $dto")
    }

    override fun toEntities(dtos: List<UserDto>): List<UserDraft> =
        dtos.map { toEntity(it) }

    override fun toDtos(entities: List<UserDraft>): List<UserDto> =
        entities.map { toDto(it) }

}
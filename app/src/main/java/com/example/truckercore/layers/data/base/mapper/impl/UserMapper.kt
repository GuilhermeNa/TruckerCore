package com.example.truckercore.layers.data.base.mapper.impl

import com.example.truckercore.data.infrastructure.security.data.Profile
import com.example.truckercore.data.infrastructure.security.data.ProfileDto
import com.example.truckercore.core.security.data.collections.PermissionSet
import com.example.truckercore.data.modules.authentication.data.UID
import com.example.truckercore.data.modules.company.data.CompanyID
import com.example.truckercore.data.modules.user.data.UserID
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.mapper.base.Mapper
import com.example.truckercore.layers.domain.model.user.User
import com.example.truckercore.layers.data.base.dto.impl.UserDto

object UserMapper : Mapper<User, UserDto> {

    override fun toDto(entity: User): UserDto =
        try {
            UserDto(
                uid = entity.uid.value,
                id = entity.id.value,
                companyId = entity.companyId.value,
                persistenceState = entity.status,
                profile = entity.profile.toDto()
            )
        } catch (e: Exception) {
            handleError(entity, e)
        }

    private fun Profile.toDto(): ProfileDto {
        return ProfileDto(
            this.role,
            this.permissions.data().toList()
        )
    }

    override fun toEntity(dto: BaseDto): User =
        try {
            User(
                uid = UID(dto.uid!!),
                id = UserID(dto.id!!),
                companyId = CompanyID(dto.companyId!!),
                status = dto.persistenceState!!,
                profile = dto.profile!!.toEntity()
            )
        } catch (e: Exception) {
            handleError(dto, e)
        }

    private fun ProfileDto.toEntity(): Profile {
        return Profile(
            role!!,
            com.example.truckercore.security.data.collections.PermissionSet(permissions!!.toSet())
        )
    }

}
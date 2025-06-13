package com.example.truckercore.model.modules.user.mapper

import com.example.truckercore.model.infrastructure.security.data.Profile
import com.example.truckercore.model.infrastructure.security.data.ProfileDto
import com.example.truckercore.model.infrastructure.security.data.collections.PermissionSet
import com.example.truckercore.model.modules._shared._contracts.mapper.Mapper
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.user.data.User
import com.example.truckercore.model.modules.user.data.UserDto
import com.example.truckercore.model.modules.user.data.UserID

object UserMapper : Mapper<User, UserDto> {

    override fun toDto(entity: User): UserDto =
        try {
            UserDto(
                uid = entity.uid.value,
                id = entity.id.value,
                companyId = entity.companyId.value,
                persistenceState = entity.persistenceState,
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

    override fun toEntity(dto: UserDto): User =
        try {
            User(
                uid = UID(dto.uid!!),
                id = UserID(dto.id!!),
                companyId = CompanyID(dto.companyId!!),
                persistenceState = dto.persistenceState!!,
                profile = dto.profile!!.toEntity()
            )
        } catch (e: Exception) {
            handleError(dto, e)
        }

    private fun ProfileDto.toEntity(): Profile {
        return Profile(
            role!!,
            PermissionSet(permissions!!.toSet())
        )
    }

}
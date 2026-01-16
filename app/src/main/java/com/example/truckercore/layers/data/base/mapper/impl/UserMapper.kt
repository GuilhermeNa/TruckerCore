package com.example.truckercore.layers.data.base.mapper.impl

import com.example.truckercore.layers.data.base.dto.impl.UserDto
import com.example.truckercore.layers.data.base.mapper.base.Mapper
import com.example.truckercore.layers.domain.model.user.User

object UserMapper : Mapper<UserDto, User> {

    override fun toDto(entity: User): UserDto =
        try {
            TODO()
            /*   UserDto(
                   uid = entity.uid.value,
                   id = entity.id.value,
                   companyId = entity.companyId.value,
                   persistenceState = entity.status,
                   profile = entity.profile.toDto()
               )*/
        } catch (e: Exception) {
            TODO()
            //handleError(entity, e)
        }

    /*    private fun ContactsContract.Profile.toDto(): ProfileDto {
            return ProfileDto(
                this.role,
                this.permissions.data().toList()
            )
        }*/

    /*    private fun ProfileDto.toEntity(): Profile {
            return Profile(
                role!!,
                com.example.truckercore.security.data.collections.PermissionSet(permissions!!.toSet())
            )
        }*/

    override fun toEntity(dto: UserDto): User {
        TODO("Not yet implemented")
    }

    override fun toDtos(entities: List<User>): List<UserDto> {
        TODO("Not yet implemented")
    }

    override fun toEntities(dtos: List<UserDto>): List<User> {
        TODO("Not yet implemented")
    }

}
package com.example.truckercore.model.modules.user.mapper

import com.example.truckercore.model.modules._contracts.Mapper
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.user.data.User
import com.example.truckercore.model.modules.user.data.UserDto
import com.example.truckercore.model.modules.user.data.UserID

object UserMapper : Mapper<User, UserDto> {

    override fun toDto(entity: User): UserDto =
        try {
            UserDto(
                uid = entity.uidVal,
                id = entity.idVal,
                companyId = entity.companyIdVal,
                persistence = entity.persistence,
                profile = entity.profile
            )
        } catch (e: Exception) {
            handleError(entity, e)
        }

    override fun toEntity(dto: UserDto): User =
        try {
            User(
                uid = UID(dto.uid!!),
                id = UserID(dto.id!!),
                companyId = CompanyID(dto.companyId!!),
                persistence = dto.persistence!!,
                profile = dto.profile!!
            )
        } catch (e: Exception) {
            handleError(dto, e)
        }

}
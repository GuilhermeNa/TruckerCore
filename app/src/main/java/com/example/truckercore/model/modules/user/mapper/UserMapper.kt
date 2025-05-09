package com.example.truckercore.model.modules.user.mapper

import com.example.truckercore.model.modules._contracts.Mapper
import com.example.truckercore.model.modules.user.data.User
import com.example.truckercore.model.modules.user.data.UserDto

object UserMapper : Mapper<User, UserDto> {

    override fun toDto(entity: User): UserDto {
        TODO()
    }

    override fun toEntity(dto: UserDto): User {
        TODO()
    }

}
package com.example.truckercore.core.config.collections

import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.dto.impl.CompanyDto
import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.model.employee.admin.data.AdminDto
import com.example.truckercore.layers.domain.model.employee.admin.data.AdminID
import com.example.truckercore.layers.data.base.dto.impl.UserDto
import com.example.truckercore.layers.domain.base.ids.UserID

object CollectionResolver {

    operator fun invoke(dto: BaseDto): AppCollection {
        return when (dto) {
            is CompanyDto -> AppCollection.COMPANY
            is UserDto -> AppCollection.USER
            is AdminDto -> AppCollection.ADMIN
            else -> throw IllegalArgumentException("Unsupported DTO type: ${dto::class.simpleName}")
        }
    }

    operator fun invoke(id: ID): AppCollection {
        return when (id) {
            is CompanyID -> AppCollection.COMPANY
            is UserID -> AppCollection.USER
            is AdminID -> AppCollection.ADMIN
            else -> throw IllegalArgumentException("Unsupported ID type: ${id::class.simpleName}")
        }
    }

    operator fun <T : BaseDto> invoke(clazz: Class<T>): AppCollection {
        return when (clazz) {
            CompanyDto::class.java -> AppCollection.COMPANY
            UserDto::class.java -> AppCollection.USER
            AdminDto::class.java -> AppCollection.ADMIN
            else -> throw IllegalArgumentException("Unsupported Class type: ${clazz::class.simpleName}")
        }
    }

}
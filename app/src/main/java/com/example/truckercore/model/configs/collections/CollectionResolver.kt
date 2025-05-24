package com.example.truckercore.model.configs.collections

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.modules._shared.contracts.entity.ID
import com.example.truckercore.model.modules.company.data.CompanyDto
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.employee.admin.data.AdminDto
import com.example.truckercore.model.modules.employee.admin.data.AdminID
import com.example.truckercore.model.modules.user.data.UserDto
import com.example.truckercore.model.modules.user.data.UserID

object CollectionResolver {

    operator fun invoke(dto: BaseDto): Collection {
        return when (dto) {
            is CompanyDto -> Collection.COMPANY
            is UserDto -> Collection.USER
            is AdminDto -> Collection.ADMIN
            else -> throw CollectionException("Unsupported DTO type: ${dto::class.simpleName}")
        }
    }

    operator fun invoke(id: ID): Collection {
        return when (id) {
            is CompanyID -> Collection.COMPANY
            is UserID -> Collection.USER
            is AdminID -> Collection.ADMIN
            else -> throw CollectionException("Unsupported ID type: ${id::class.simpleName}")
        }
    }

    operator fun <T: BaseDto> invoke(clazz: Class<T>): Collection {
        return when(clazz) {
            CompanyDto::class.java -> Collection.COMPANY
            UserDto::class.java -> Collection.USER
            AdminDto::class.java -> Collection.ADMIN
            else -> throw CollectionException("Unsupported DTO type: ${clazz::class.simpleName}")
        }
    }

}
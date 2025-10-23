package com.example.truckercore.layers.domain.model.employee.admin.specification

import com.example.truckercore.data.infrastructure.repository.data.contracts.Specification
import com.example.truckercore.data.infrastructure.repository.data.abstractions.WhereEqual
import com.example.truckercore.data.modules.employee.admin.data.AdminDto
import com.example.truckercore.data.modules.user.data.UserID

data class AdminSpec(
    override val entityId: com.example.truckercore.domain._shared._contracts.entity.ID? = null,
    val userId: UserID? = null
) : Specification<AdminDto> {

    init {
        validate()
    }

    override val dtoClass = AdminDto::class.java

    override fun getFilter(): com.example.truckercore.data.repository.data.abstractions.SearchFilter {
        val searchFilter = com.example.truckercore.data.repository.data.abstractions.SearchFilter()

        userId?.let { searchFilter.add(WhereEqual(com.example.truckercore.core.config.enums.Field.USER_ID, it.value)) }

        return searchFilter
    }

}

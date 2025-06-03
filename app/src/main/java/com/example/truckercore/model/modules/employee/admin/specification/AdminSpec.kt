package com.example.truckercore.model.modules.employee.admin.specification

import com.example.truckercore.model.configs.enums.Field
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.collections.SearchFilter
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.filters.WhereEqual
import com.example.truckercore.model.modules._shared._contracts.entity.ID
import com.example.truckercore.model.modules.employee.admin.data.AdminDto
import com.example.truckercore.model.modules.user.data.UserID

data class AdminSpec(
    override val entityId: ID? = null,
    val userId: UserID? = null
) : Specification<AdminDto> {

    init {
        validate()
    }

    override val dtoClass = AdminDto::class.java

    override fun getFilter(): SearchFilter {
        val searchFilter = SearchFilter()

        userId?.let { searchFilter.add(WhereEqual(Field.USER_ID, it.value)) }

        return searchFilter
    }

}

package com.example.truckercore.model.modules.user.specification

import com.example.truckercore.model.configs.enums.Field
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.collections.SearchFilter
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.filters.WhereEqual
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.user.data.UserDto
import com.example.truckercore.model.modules.user.data.UserID

data class UserSpec(
    override val entityId: UserID? = null,
    val companyId: CompanyID? = null,
    val category: Role? = null,
    val uid: UID? = null,
) : Specification<UserDto> {

    override val dtoClass = UserDto::class.java

    override fun getFilter(): SearchFilter {
        val searchFilter = SearchFilter()

        uid?.let { searchFilter.add(WhereEqual(Field.UID, it.value)) }
        companyId?.let { searchFilter.add(WhereEqual(Field.COMPANY_ID, it.value)) }
        category?.let { searchFilter.add(WhereEqual(Field.CATEGORY, it)) }

        return searchFilter
    }

}

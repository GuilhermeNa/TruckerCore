package com.example.truckercore.layers.data.base.specification.specs_impl

import com.example.truckercore.data.infrastructure.repository.data.contracts.Specification
import com.example.truckercore.data.infrastructure.repository.data.abstractions.WhereEqual
import com.example.truckercore.data.infrastructure.security.data.enums.Role
import com.example.truckercore.data.modules.authentication.data.UID
import com.example.truckercore.data.modules.company.data.CompanyID
import com.example.truckercore.data.modules.user.data.UserDto
import com.example.truckercore.data.modules.user.data.UserID

data class UserSpec(
    override val entityId: UserID? = null,
    val companyId: CompanyID? = null,
    val category: Role? = null,
    val uid: UID? = null,
) : Specification<UserDto> {

    override val dtoClass = UserDto::class.java

    override fun getFilter(): com.example.truckercore.data.repository.data.abstractions.SearchFilter {
        val searchFilter = com.example.truckercore.data.repository.data.abstractions.SearchFilter()

        uid?.let { searchFilter.add(WhereEqual(com.example.truckercore.core.config.enums.Field.UID, it.value)) }
        companyId?.let { searchFilter.add(WhereEqual(com.example.truckercore.core.config.enums.Field.COMPANY_ID, it.value)) }
        category?.let { searchFilter.add(WhereEqual(com.example.truckercore.core.config.enums.Field.CATEGORY, it)) }

        return searchFilter
    }

}

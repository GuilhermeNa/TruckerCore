package com.example.truckercore.layers.data.base.specification.specs_impl

import com.example.truckercore.core.config.enums.Field
import com.example.truckercore.layers.data.base.dto.impl.UserDto
import com.example.truckercore.layers.data.base.filter.collection.SearchFilters
import com.example.truckercore.layers.data.base.filter.filters.WhereEqual
import com.example.truckercore.layers.data.base.specification._contracts.Specification
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.access.Role

data class UserSpec(
    override val entityId: UserID? = null,
    val companyId: CompanyID? = null,
    val category: Role? = null,
    val uid: UID? = null,
) : Specification<UserDto> {

    override val dtoClass = UserDto::class.java

    override fun getFilter(): SearchFilters {
        val searchFilter = SearchFilters()

        uid?.let { searchFilter.add(WhereEqual(Field.UID, it.value)) }
        companyId?.let { searchFilter.add(WhereEqual(Field.COMPANY_ID, it.value)) }
        category?.let { searchFilter.add(WhereEqual(Field.CATEGORY, it)) }

        return searchFilter
    }

}

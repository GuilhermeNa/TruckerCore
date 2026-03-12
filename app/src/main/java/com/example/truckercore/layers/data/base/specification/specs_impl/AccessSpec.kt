package com.example.truckercore.layers.data.base.specification.specs_impl

import com.example.truckercore.core.config.enums.Field
import com.example.truckercore.layers.data.base.dto.impl.AccessDto
import com.example.truckercore.layers.data.base.filter.collection.SearchFilters
import com.example.truckercore.layers.data.base.filter.filters.WhereEqual
import com.example.truckercore.layers.data.base.specification._contracts.Specification
import com.example.truckercore.layers.domain.base.contracts.ID
import com.example.truckercore.layers.domain.base.ids.UserID

data class AccessSpec(
    override val entityId: ID? = null,
    val userId: UserID? = null
): Specification<AccessDto> {

    override val dtoClass = AccessDto::class.java

    override fun getFilter(): SearchFilters {
        val searchFilter = SearchFilters()

        userId?.let { searchFilter.add(WhereEqual(Field.USER_ID, it.value)) }

        return searchFilter
    }

}

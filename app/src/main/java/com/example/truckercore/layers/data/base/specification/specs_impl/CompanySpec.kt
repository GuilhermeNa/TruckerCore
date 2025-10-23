package com.example.truckercore.layers.data.base.specification.specs_impl

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.layers.data.base.dto.impl.CompanyDto
import com.example.truckercore.layers.data.base.filter.collection.SearchFilters
import com.example.truckercore.layers.data.base.specification._contracts.Specification
import com.example.truckercore.layers.domain.base.ids.CompanyID

class CompanySpec(
    override val entityId: CompanyID? = null
) : Specification<CompanyDto> {

    override val dtoClass = CompanyDto::class.java

    override val collection = AppCollection.COMPANY

    override fun getFilter(): SearchFilters {
        TODO()
    }

}
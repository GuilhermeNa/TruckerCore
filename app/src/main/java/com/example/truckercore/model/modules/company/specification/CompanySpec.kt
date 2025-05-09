package com.example.truckercore.model.modules.company.specification

import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Filter
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification
import com.example.truckercore.model.modules.company.data.CompanyDto
import com.example.truckercore.model.modules.company.data.CompanyID

class CompanySpec(
    override val entityId: CompanyID? = null
) : Specification<CompanyDto> {

    override val dtoClass = CompanyDto::class.java

    override val collection = Collection.COMPANY

    override fun getFilters(): List<Filter> {
        TODO()
    }

}
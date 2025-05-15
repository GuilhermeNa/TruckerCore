package com.example.truckercore.model.modules.company.specification

import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Filter
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification
import com.example.truckercore.model.modules.company.data.CompanyDto
import com.example.truckercore.model.modules.company.data.CompanyID

class CompanySpec(
    override val entityId: CompanyID? = null
) : Specification<CompanyDto> {

    override val dtoClass = CompanyDto::class.java

    override val collection = Collection.COMPANY

    override fun getFilter(): List<Filter> {
        TODO()
    }

}
package com.example.truckercore.model.modules.employee.admin.specification

import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Filter
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification
import com.example.truckercore.model.modules._contracts.ID
import com.example.truckercore.model.modules.employee.admin.data.AdminDto

data class AdminSpec(
    override val entityId: ID? = null
): Specification<AdminDto> {

    override val dtoClass = AdminDto::class.java
    override val collection = Collection.ADMIN

    override fun getFilters(): List<Filter> {
        TODO("Not yet implemented")
    }

}

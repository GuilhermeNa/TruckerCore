package com.example.truckercore.model.modules.employee.admin.specification

import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.configs.enums.Field
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Filter
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.filters.WhereEqual
import com.example.truckercore.model.modules._contracts.ID
import com.example.truckercore.model.modules.employee.admin.data.AdminDto
import com.example.truckercore.model.modules.user.data.UserID

data class AdminSpec(
    override val entityId: ID? = null,
    val userId: UserID? = null
): Specification<AdminDto> {

    override val dtoClass = AdminDto::class.java
    override val collection = Collection.ADMIN

    override fun getFilters(): List<Filter> {
        val ml = mutableListOf<Filter>()

        userId?.let { ml.add(WhereEqual(Field.USER_ID, it.value)) }

        return ml
    }

}

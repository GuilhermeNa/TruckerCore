package com.example.truckercore.model.modules.employee.autonomous.specification

import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.configs.enums.Field
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.collections.SearchFilter
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.filters.WhereEqual
import com.example.truckercore.model.modules._shared.contracts.entity.ID
import com.example.truckercore.model.modules.employee.autonomous.data.AutonomousDto
import com.example.truckercore.model.modules.user.data.UserID

data class AutonomousSpec(
    override val entityId: ID? = null,
    val userId: UserID? = null
) : Specification<AutonomousDto> {

    override val collection = Collection.AUTONOMOUS

    override val dtoClass = AutonomousDto::class.java

    override fun getFilter(): SearchFilter {
        val searchFilter = SearchFilter()

        userId?.let { searchFilter.add(WhereEqual(Field.USER_ID, it.value)) }

        return searchFilter
    }

}


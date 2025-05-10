package com.example.truckercore.model.modules.employee.autonomous.specification

import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.configs.enums.Field
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Filter
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.filters.WhereEqual
import com.example.truckercore.model.modules._contracts.ID
import com.example.truckercore.model.modules.employee.autonomous.data.AutonomousDto
import com.example.truckercore.model.modules.user.data.UserID

data class AutonomousSpec(
    override val entityId: ID? = null,
    val userId: UserID? = null
) : Specification<AutonomousDto> {

    override val collection = Collection.AUTONOMOUS

    override val dtoClass = AutonomousDto::class.java

    override fun getFilters(): List<Filter> {
        val ml = mutableListOf<Filter>()

        userId?.let { ml.add(WhereEqual(Field.USER_ID, it.value)) }

        return ml
    }

}


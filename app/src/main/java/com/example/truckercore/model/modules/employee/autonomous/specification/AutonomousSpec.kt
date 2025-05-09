package com.example.truckercore.model.modules.employee.autonomous.specification

import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Filter
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification
import com.example.truckercore.model.modules._contracts.ID
import com.example.truckercore.model.modules.employee.autonomous.data.AutonomousDto

data class AutonomousSpec(
    override val entityId: ID? = null
) : Specification<AutonomousDto> {

    override val collection = Collection.AUTONOMOUS

    override val dtoClass = AutonomousDto::class.java

    override fun getFilters(): List<Filter> {
        TODO("Not yet implemented")
    }

}


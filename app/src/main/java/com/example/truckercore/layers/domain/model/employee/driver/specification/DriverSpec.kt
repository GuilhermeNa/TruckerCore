package com.example.truckercore.layers.domain.model.employee.driver.specification

import com.example.truckercore.data.infrastructure.repository.data.contracts.Specification
import com.example.truckercore.data.infrastructure.repository.data.abstractions.WhereEqual
import com.example.truckercore.data.modules.employee.driver.data.DriverDto
import com.example.truckercore.data.modules.employee.driver.data.DriverID
import com.example.truckercore.data.modules.user.data.UserID

data class DriverSpec(
    override val entityId: DriverID? = null,
    val userId: UserID? = null
) : Specification<DriverDto> {

    override val dtoClass = DriverDto::class.java

    override val collection = com.example.truckercore.core.config.collections.AppCollection.DRIVER

    override fun getFilter(): com.example.truckercore.data.repository.data.abstractions.SearchFilter {
        val searchFilter = com.example.truckercore.data.repository.data.abstractions.SearchFilter()

        userId?.let { searchFilter.add(WhereEqual(com.example.truckercore.core.config.enums.Field.USER_ID, it.value)) }

        return searchFilter
    }

}

package com.example.truckercore.model.modules.employee.driver.specification

import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.configs.enums.Field
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Filter
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.filters.WhereEqual
import com.example.truckercore.model.modules.employee.driver.data.DriverDto
import com.example.truckercore.model.modules.employee.driver.data.DriverID
import com.example.truckercore.model.modules.user.data.UserID

data class DriverSpec(
    override val entityId: DriverID? = null,
    val userId: UserID? = null
) : Specification<DriverDto> {

    override val dtoClass = DriverDto::class.java

    override val collection = Collection.DRIVER

    override fun getFilter(): List<Filter> {
        val ml = mutableListOf<Filter>()

        userId?.let { ml.add(WhereEqual(Field.USER_ID, it.value)) }

        return ml
    }

}

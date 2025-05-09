package com.example.truckercore.model.modules.employee.driver.specification

import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Filter
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification
import com.example.truckercore.model.modules.employee.driver.data.DriverDto
import com.example.truckercore.model.modules.employee.driver.data_helper.DriverID

data class DriverSpec(
    override val entityId: DriverID? = null
) : Specification<DriverDto> {

    override val dtoClass = DriverDto::class.java

    override val collection = Collection.DRIVER

    override fun getFilters(): List<Filter> {
        TODO("Not yet implemented")
    }

}

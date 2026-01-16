package com.example.truckercore.layers.data.base.specification.specs_impl

import com.example.truckercore.layers.data.base.dto.impl.TruckDto
import com.example.truckercore.layers.data.base.filter.collection.SearchFilters
import com.example.truckercore.layers.data.base.specification._contracts.Specification
import com.example.truckercore.layers.domain.base.ids.TruckID

class TruckSpec(
    override val entityId: TruckID? = null
) : Specification<TruckDto> {

    override val dtoClass = TruckDto::class.java

    override fun getFilter(): SearchFilters {
        TODO("Not yet implemented")
    }


}
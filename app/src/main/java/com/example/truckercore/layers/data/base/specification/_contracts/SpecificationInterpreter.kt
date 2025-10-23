package com.example.truckercore.layers.data.base.specification._contracts

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.layers.data.base.filter.collection.SearchFilters
import com.example.truckercore.layers.domain.base.contracts.entity.ID

interface SpecificationInterpreter {

    fun byId(id: ID, collection: AppCollection): ApiSpecificationWrapper

    fun byFilter(searchFilter: SearchFilters, collection: AppCollection): ApiSpecificationWrapper

}

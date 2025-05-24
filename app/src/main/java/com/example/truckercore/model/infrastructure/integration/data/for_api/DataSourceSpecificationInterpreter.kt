package com.example.truckercore.model.infrastructure.integration.data.for_api

import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.infrastructure.integration.data.for_api.data.contracts.ApiSpecification
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.collections.SearchFilter
import com.example.truckercore.model.modules._shared.contracts.entity.ID

interface DataSourceSpecificationInterpreter {

    fun byId(id: ID, collection: Collection): ApiSpecification

    fun byFilter(searchFilter: SearchFilter, collection: Collection): ApiSpecification

}

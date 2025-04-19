package com.example.truckercore.model.infrastructure.integration._data.for_app.specification

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.exceptions.SpecificationException
import com.example.truckercore.model.shared.interfaces.data.ID
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto

interface Specification<T : BaseDto> {

    val dtoClass: Class<T>

    val entityId: ID?
    val id
        get() = entityId?.value ?: throw SpecificationException(
            "The specification does not contain a valid entity ID: $this"
        )

    val collection: Collection
    val collectionName get() = collection.name

    fun getFilters(): List<Filter>

}
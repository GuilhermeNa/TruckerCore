package com.example.truckercore.model.modules._shared.contracts.mapper

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.modules._shared.contracts.entity.BaseEntity

interface MapperErrorHandler {

    fun handleError(dto: BaseDto, e: Throwable): Nothing {
        val message = "$DTO_ERR_MSG $dto"
        throw MapperException(message, e)
    }

    fun handleError(entity: BaseEntity<*>, e: Throwable): Nothing {
        val message = "$ENTITY_ERR_MSG $entity"
        throw MapperException(message, e)
    }

    companion object {
        private const val DTO_ERR_MSG = "Error while mapping an dto to entity:"
        private const val ENTITY_ERR_MSG = "Error while mapping an entity to dto:"
    }

}
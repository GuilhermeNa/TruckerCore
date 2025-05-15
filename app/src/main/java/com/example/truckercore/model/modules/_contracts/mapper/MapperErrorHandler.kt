package com.example.truckercore.model.modules._contracts.mapper

import com.example.truckercore.model.errors.technical.TechnicalException
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.modules._contracts.BaseEntity

interface MapperErrorHandler {

    fun handleError(dto: BaseDto, e: Throwable): Nothing {
        val message = "$DTO_ERR_MSG $dto"
        throw TechnicalException.MappingDtoToEntity(message, e)
    }

    fun handleError(entity: BaseEntity, e: Throwable): Nothing {
        val message = "$ENTITY_ERR_MSG $entity"
        throw TechnicalException.MappingEntityToDto(message, e)
    }

    companion object {
        private const val DTO_ERR_MSG = "Error while mapping an dto to entity:"
        private const val ENTITY_ERR_MSG = "Error while mapping an entity to dto:"
    }

}
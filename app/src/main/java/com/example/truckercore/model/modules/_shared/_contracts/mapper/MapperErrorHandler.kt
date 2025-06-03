package com.example.truckercore.model.modules._shared._contracts.mapper

import com.example.truckercore._shared.expressions.getClassName
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.model.modules._shared._contracts.entity.BaseEntity

interface MapperErrorHandler {

    fun handleError(dto: BaseDto, e: Throwable): Nothing {
        val message = "$DTO_ERR_MSG $dto"
        AppLogger.e(getClassName(), message, e)
        throw MapperException(message, e)
    }

    fun handleError(entity: BaseEntity<*>, e: Throwable): Nothing {
        val message = "$ENTITY_ERR_MSG $entity"
        AppLogger.e(getClassName(), message, e)
        throw MapperException(message, e)
    }

    companion object {
        private const val DTO_ERR_MSG = "Error while mapping an dto to entity:"
        private const val ENTITY_ERR_MSG = "Error while mapping an entity to dto:"
    }

}
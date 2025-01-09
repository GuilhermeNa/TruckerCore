package com.example.truckercore.shared.services

import com.example.truckercore.modules.business_central.errors.BusinessCentralMappingException
import com.example.truckercore.modules.business_central.errors.BusinessCentralValidationException
import com.example.truckercore.shared.errors.InvalidEnumParameterException
import com.example.truckercore.shared.errors.MissingFieldException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.Mapper
import com.example.truckercore.shared.utils.Response
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn

internal class ResponseHandlerService<E : Entity, D : Dto>(
    private val validatorService: ValidatorService<E, D>,
    private val mapper: Mapper<E, D>
) {

    fun processResponse(response: Response<D>): Response<E> {
        return when (response) {
            is Response.Success -> handleSuccessResponse(response)
            is Response.Error -> handleErrorResponse(response)
            is Response.Empty -> handleEmptyResponse(response)
        }
    }

    private fun handleSuccessResponse(response: Response.Success<D>): Response<E> = try {
        validatorService.validateDto(response.data)
        val entity = mapper.toEntity(response.data)
        Response.Success(entity)

    } catch (e: BusinessCentralValidationException) {
        logError("Error during DTO validation. ${e.message}")
        Response.Error(exception = e)

    } catch (e: BusinessCentralMappingException) {
        logError("Error during DTO mapping. ${e.message}")
        Response.Error(exception = e)

    }

    private fun handleErrorResponse(response: Response.Error): Response.Error {
        logError("Error while handling with database response: ${response.message}")
        return response
    }

    private fun handleEmptyResponse(response: Response.Empty): Response.Empty {
        logWarn("The database response came back empty.")
        return response
    }

    fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("An unexpected error occurred during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }

}
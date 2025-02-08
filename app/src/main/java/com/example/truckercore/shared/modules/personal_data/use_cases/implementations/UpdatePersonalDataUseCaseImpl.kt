package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.personal_data.mapper.PersonalDataMapper
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.CheckPersonalDataExistenceUseCase
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.UpdatePersonalDataUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class UpdatePersonalDataUseCaseImpl(
    private val repository: PersonalDataRepository,
    private val checkExistence: CheckPersonalDataExistenceUseCase,
    private val validatorService: ValidatorService,
    private val mapper: PersonalDataMapper,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), UpdatePersonalDataUseCase {

    override suspend fun execute(user: User, pData: PersonalData): Flow<Response<Unit>> = flow {
        val result =
            if (userHasPermission(user)) verifyExistence(user, pData)
            else handleUnauthorizedPermission(user, pData.id!!)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun verifyExistence(user: User, pData: PersonalData): Response<Unit> =
        when (val existenceResponse = checkExistence.execute(user, pData.id!!).single()) {
            is Response.Success -> processUpdate(pData)
            is Response.Empty -> handleNonExistentObject(pData.id)
            is Response.Error -> logAndReturnResponse(existenceResponse)
        }

    private suspend fun processUpdate(pData: PersonalData): Response<Unit> {
        validatorService.validateEntity(pData)
        val dto = mapper.toDto(pData)
        return repository.update(dto).single()
    }

}
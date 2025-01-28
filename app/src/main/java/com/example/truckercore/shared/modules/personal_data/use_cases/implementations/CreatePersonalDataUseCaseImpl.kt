package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.personal_data.mapper.PersonalDataMapper
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.CreatePersonalDataUseCase
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class CreatePersonalDataUseCaseImpl(
    private val repository: PersonalDataRepository,
    private val validatorService: ValidatorService,
    private val permissionService: PermissionService,
    private val mapper: PersonalDataMapper
) : UseCase(), CreatePersonalDataUseCase {

    override suspend fun execute(user: User, pData: PersonalData): Flow<Response<String>> = flow {
        val result =
            if (userHasPermission(user)) processCreation(pData)
            else handleUnauthorizedPermission(user, pData.id!!)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.CREATE_PERSONAL_DATA)

    private suspend fun processCreation(pData: PersonalData): Response<String> {
        validatorService.validateForCreation(pData)
        val pDataDto = mapper.toDto(pData)
        return repository.create(pDataDto).single()
    }

}
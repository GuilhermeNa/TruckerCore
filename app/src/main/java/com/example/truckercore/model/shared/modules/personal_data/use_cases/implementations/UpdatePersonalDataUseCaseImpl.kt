package com.example.truckercore.model.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.model.shared.modules.personal_data.mapper.PersonalDataMapper
import com.example.truckercore.model.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.CheckPersonalDataExistenceUseCase
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.UpdatePersonalDataUseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class UpdatePersonalDataUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: PersonalDataRepository,
    private val checkExistence: CheckPersonalDataExistenceUseCase,
    private val validatorService: ValidatorService,
    private val mapper: PersonalDataMapper
) : UseCase(permissionService), UpdatePersonalDataUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun execute(user: User, pData: PersonalData): Flow<Response<Unit>> {
        val id = pData.id ?: throw NullPointerException("Null PersonalData id while updating.")

        return checkExistence.execute(user, id).flatMapConcat { response ->
            if (response.isEmpty()) throw ObjectNotFoundException(
                "Attempting to update a PersonalData that was not found for id: $id."
            )
            user.runIfPermitted { processUpdate(pData) }
        }
    }

    private fun processUpdate(pData: PersonalData): Flow<Response<Unit>> {
        validatorService.validateEntity(pData)
        val dto = mapper.toDto(pData)
        return repository.update(dto)
    }

}
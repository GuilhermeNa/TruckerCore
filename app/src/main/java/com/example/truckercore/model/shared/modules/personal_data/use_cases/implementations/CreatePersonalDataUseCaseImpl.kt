package com.example.truckercore.model.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.model.shared.modules.personal_data.mapper.PersonalDataMapper
import com.example.truckercore.model.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.CreatePersonalDataUseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class CreatePersonalDataUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: PersonalDataRepository,
    private val validatorService: ValidatorService,
    private val mapper: PersonalDataMapper
) : UseCase(permissionService), CreatePersonalDataUseCase {

    override suspend fun execute(user: User, pData: PersonalData): Flow<Response<String>> =
        user.runIfPermitted { processCreation(pData) }

    private fun processCreation(pData: PersonalData): Flow<Response<String>> {
        validatorService.validateForCreation(pData)
        val pDataDto = mapper.toDto(pData)
        return repository.create(pDataDto)
    }

}
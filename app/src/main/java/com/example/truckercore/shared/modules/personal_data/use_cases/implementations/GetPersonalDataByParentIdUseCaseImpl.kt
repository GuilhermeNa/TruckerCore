package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.personal_data.mapper.PersonalDataMapper
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataByParentIdUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class GetPersonalDataByParentIdUseCaseImpl(
    private val repository: PersonalDataRepository,
    private val validatorService: ValidatorService,
    private val mapper: PersonalDataMapper,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), GetPersonalDataByParentIdUseCase {

    override suspend fun execute(
        user: User,
        parentId: String
    ): Flow<Response<List<PersonalData>>> = flow {
        parentId.validateIsNotBlank(Field.PARENT_ID.getName())

        val result =
            if (userHasPermission(user)) fetchByParentId(parentId)
            else handleUnauthorizedPermission(user, parentId)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun fetchByParentId(parentId: String): Response<List<PersonalData>> =
        when (val response = repository.fetchByParentId(parentId).single()) {
            is Response.Success -> processData(response.data)
            is Response.Error -> logAndReturnResponse(response)
            is Response.Empty -> response
        }

    private fun processData(dtos: List<PersonalDataDto>): Response<List<PersonalData>> {
        dtos.forEach { validatorService.validateDto(it) }
        val data = dtos.map { mapper.toEntity(it) }
        return Response.Success(data)
    }

}
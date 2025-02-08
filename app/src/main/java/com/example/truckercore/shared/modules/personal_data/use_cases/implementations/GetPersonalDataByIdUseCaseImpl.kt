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
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataByIdUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class GetPersonalDataByIdUseCaseImpl(
    private val repository: PersonalDataRepository,
    private val validatorService: ValidatorService,
    private val mapper: PersonalDataMapper,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), GetPersonalDataByIdUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<PersonalData>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) fetchById(id)
            else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun fetchById(id: String): Response<PersonalData> =
        when (val response = repository.fetchById(id).single()) {
            is Response.Success -> processResponse(response)
            is Response.Error -> logAndReturnResponse(response)
            is Response.Empty -> response
        }

    private fun processResponse(response: Response.Success<PersonalDataDto>): Response<PersonalData> {
        validatorService.validateDto(response.data)
        val entity = mapper.toEntity(response.data)
        return Response.Success(entity)
    }

}
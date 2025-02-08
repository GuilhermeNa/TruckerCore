package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.CheckPersonalDataExistenceUseCase
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.DeletePersonalDataUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class DeletePersonalDataUseCaseImpl(
    private val repository: PersonalDataRepository,
    private val checkExistence: CheckPersonalDataExistenceUseCase,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), DeletePersonalDataUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Unit>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) verifyExistence(user, id)
            else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun verifyExistence(user: User, id: String): Response<Unit> =
        when (val existenceResponse = checkExistence.execute(user, id).single()) {
            is Response.Success -> deleteAdmin(id)
            is Response.Empty -> handleNonExistentObject(id)
            is Response.Error -> logAndReturnResponse(existenceResponse)
        }

    private suspend fun deleteAdmin(id: String): Response<Unit> {
        return repository.delete(id).single()
    }

}
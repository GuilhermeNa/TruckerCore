package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.CheckPersonalDataExistenceUseCase
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.DeletePersonalDataUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class DeletePersonalDataUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: PersonalDataRepository,
    private val checkExistence: CheckPersonalDataExistenceUseCase
) : UseCase(permissionService), DeletePersonalDataUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun execute(user: User, id: String): Flow<Response<Unit>> =
        checkExistence.execute(user, id).flatMapConcat { response ->
            if (response.isEmpty()) throw ObjectNotFoundException(
                "Attempting to delete a PersonalData that was not found for id: $id."
            )
            user.runIfPermitted { repository.delete(id) }
        }

}
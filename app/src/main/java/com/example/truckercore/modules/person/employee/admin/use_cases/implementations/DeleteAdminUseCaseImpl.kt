package com.example.truckercore.modules.person.employee.admin.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.person.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.CheckAdminExistenceUseCase
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.DeleteAdminUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class DeleteAdminUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: AdminRepository,
    private val checkExistence: CheckAdminExistenceUseCase
) : UseCase(permissionService), DeleteAdminUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(user: User, id: String): Flow<Response<Unit>> =
        checkExistence.execute(user, id).flatMapConcat { response ->
            if (response.isEmpty()) throw ObjectNotFoundException(
                "Attempting to delete a Admin that was not found for id: $id."
            )
            user.runIfPermitted { repository.delete(id) }
        }

}
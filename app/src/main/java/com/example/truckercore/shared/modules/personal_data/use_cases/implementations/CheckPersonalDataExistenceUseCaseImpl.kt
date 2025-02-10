package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.CheckPersonalDataExistenceUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class CheckPersonalDataExistenceUseCaseImpl(
    private val repository: PersonalDataRepository,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), CheckPersonalDataExistenceUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Unit>> =
        user.runIfPermitted { repository.entityExists(id) }

}
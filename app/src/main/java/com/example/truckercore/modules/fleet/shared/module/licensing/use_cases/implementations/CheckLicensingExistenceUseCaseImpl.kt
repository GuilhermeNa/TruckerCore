package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.CheckLicensingExistenceUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class CheckLicensingExistenceUseCaseImpl(
    private val repository: LicensingRepository,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), CheckLicensingExistenceUseCase {

    override fun execute(user: User, id: String): Flow<Response<Unit>> =
        user.runIfPermitted { repository.entityExists(id) }

}
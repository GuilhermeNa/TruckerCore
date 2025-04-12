package com.example.truckercore.model.modules.person.employee.admin.use_cases.interfaces

import com.example.truckercore.model.modules.person.employee.admin.entity.Admin
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for checking the existence of a [Admin] entity by its ID.
 */
internal interface CheckAdminExistenceUseCase {

    /**
     * Executes the use case to check if an [Admin] entity exists by its ID.
     *
     * @param user The [User] who is making the request.
     * @param id The ID of the [Admin] entity to check for existence.
     * @return A [Flow] of:
     * - [AppResponse.Success] when the object exists.
     * - [AppResponse.Empty] when the object does not exist.
     */
    fun execute(user: User, id: String): Flow<AppResponse<Unit>>

}
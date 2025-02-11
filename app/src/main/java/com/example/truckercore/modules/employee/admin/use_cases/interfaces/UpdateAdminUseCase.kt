package com.example.truckercore.modules.employee.admin.use_cases.interfaces

import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for updating an [Admin] entity.
 *
 * This use case involves updating an existing [Admin] in the system, which may include
 * validating the data, checking permissions, and applying business rules.
 *
 * @see Admin
 * @see Response
 */
internal interface UpdateAdminUseCase {

    /**
     * Executes the use case to update an existing [Admin] entity.
     *
     * @param user The [User] performing the update. This is used to check if the user has the necessary permissions.
     * @param admin The [Admin] entity that is being updated.
     * @return A [Flow] of [Response.Success] if the update was successful.
     * @throws ObjectNotFoundException if the object to be updated is not found.
     */
    fun execute(user: User, admin: Admin): Flow<Response<Unit>>

}
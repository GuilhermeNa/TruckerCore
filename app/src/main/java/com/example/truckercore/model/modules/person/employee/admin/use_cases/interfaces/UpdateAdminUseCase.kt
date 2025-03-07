package com.example.truckercore.model.modules.person.employee.admin.use_cases.interfaces

import com.example.truckercore.model.modules.person.employee.admin.entity.Admin
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.utils.sealeds.Response
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
     * Executes the use case to update an existing [Admin] entity in the system.
     * This method checks the existence of the admin, validates its data, and applies the necessary business rules
     * before updating the admin in the repository.
     *
     * @param user The [User] performing the update. The user must have the required permissions for the operation.
     * @param admin The [Admin] entity to be updated.
     * @return A [Flow] of [Response.Success] when the update is successful.
     * @throws NullPointerException if the admin entity's ID is null during the update process.
     * @throws ObjectNotFoundException if the admin entity to be updated is not found in the system.
     */
    fun execute(user: User, admin: Admin): Flow<Response<Unit>>

}
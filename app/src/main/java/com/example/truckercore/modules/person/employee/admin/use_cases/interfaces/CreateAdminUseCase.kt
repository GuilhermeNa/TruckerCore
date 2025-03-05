package com.example.truckercore.modules.person.employee.admin.use_cases.interfaces

import com.example.truckercore.modules.person.employee.admin.entity.Admin
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines the use case for creating a new [Admin] entity.
 *
 * This use case encapsulates the business logic required to create a new [Admin] entity.
 * It handles permission checks, validation, and delegates the actual creation to a repository.
 *
 * @see Admin
 * @see Response
 */
internal interface CreateAdminUseCase {

    /**
     * Executes the use case to create a new [Admin] entity.
     *
     * @param user The [User] who is requesting the creation. This parameter might be used for permission checks or tracking.
     * @param admin The [Admin] entity to be created. This object contains the data to be stored in the system.
     * @return A [Flow] of [Response.Success] when the object is successfully created.
     */
    fun execute(user: User, admin: Admin): Flow<Response<String>>

}
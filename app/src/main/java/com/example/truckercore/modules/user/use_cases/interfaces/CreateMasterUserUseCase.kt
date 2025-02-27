package com.example.truckercore.modules.user.use_cases.interfaces

import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for creating a new [User] entity during the authentication process.
 *
 * This interface defines the contract for a use case responsible for creating the first Master user of a new account.
 * It handles the logic required to insert a new user into the system, such as validating data, applying business rules,
 * and interacting with the persistence layer (e.g., database). This use case is specifically used for authenticating
 * and creating the first user of a new account.
 */
internal interface CreateMasterUserUseCase {

    /**
     * Executes the use case to create the first [User] entity for a new account.
     *
     * @param masterUser The User entity to be created. This object contains the data to be stored in the system.
     * @return A [Response] string representing the unique identifier of the newly created User entity.
     * @throws InvalidStateException when the user is not in [Level.MASTER]
     */
    fun execute(masterUser: User): Flow<Response<String>>

}
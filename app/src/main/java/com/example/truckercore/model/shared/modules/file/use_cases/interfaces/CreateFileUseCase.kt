package com.example.truckercore.model.shared.modules.file.use_cases.interfaces

import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.modules.file.entity.File
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines the use case for creating a new [File] entity.
 *
 * @see File
 * @see AppResponse
 */
internal interface CreateFileUseCase {

    /**
     * Executes the use case to create a new [File] entity.
     *
     * @param user The [User] who is requesting the creation. This parameter might be used for permission checks or tracking.
     * @param file The [File] entity to be created. This object contains the data to be stored in the system.
     * @return A [Flow] of [AppResponse.Success] when the object is successfully created.
     */
    fun execute(user: User, file: File): Flow<AppResponse<String>>

}
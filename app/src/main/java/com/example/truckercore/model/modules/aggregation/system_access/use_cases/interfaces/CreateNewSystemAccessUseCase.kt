package com.example.truckercore.model.modules.aggregation.system_access.use_cases.interfaces

import com.example.truckercore.model.modules.aggregation.system_access.factory.SystemAccessForm
import com.example.truckercore._utils.classes.AppResult

/**
 * Use case interface for creating new system access for a user.
 *
 * This contract defines the operation for onboarding a new user into the system,
 * based on the data provided through a [SystemAccessForm]. The implementation is
 * responsible for validating, constructing, and persisting the necessary domain entities
 * (e.g., user, company, employee) to enable access.
 *
 * The result is returned as an [AppResult], indicating success or a failure with a wrapped exception.
 *
 * @param form The form containing user input data required for access creation.
 * @return [AppResult] representing the outcome of the operation.
 */
interface CreateNewSystemAccessUseCase {

    suspend operator fun invoke(form: SystemAccessForm): AppResult<Unit>

}
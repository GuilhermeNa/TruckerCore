package com.example.truckercore.modules.user.service

import com.example.truckercore.shared.abstractions.Service
import com.example.truckercore.infrastructure.util.ExceptionHandler
import com.example.truckercore.modules.user.aggregations.UserWithPerson
import com.example.truckercore.modules.user.use_cases.interfaces.AggregateUserWithPersonUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class UserServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val getUserWithPerson: AggregateUserWithPersonUseCase
) : Service(exceptionHandler), UserService {

    override fun fetchLoggedUserWithPerson(
        userId: String, shouldStream: Boolean
    ): Flow<Response<UserWithPerson>> =
        runSafe { getUserWithPerson.execute(userId, shouldStream) }

}
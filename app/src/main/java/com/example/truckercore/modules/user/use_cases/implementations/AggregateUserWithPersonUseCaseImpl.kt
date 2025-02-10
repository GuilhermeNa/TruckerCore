package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.modules.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.modules.user.aggregations.UserWithPerson
import com.example.truckercore.modules.user.use_cases.interfaces.AggregateUserWithPersonUseCase
import com.example.truckercore.modules.user.use_cases.interfaces.GetUserUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

internal class AggregateUserWithPersonUseCaseImpl(
    private val getUser: GetUserUseCase,
    private val getDriver: GetDriverUseCase,
    private val getAdminUseCase: GetAdminUseCase
) : AggregateUserWithPersonUseCase {

    override fun execute(userId: String, shouldStream: Boolean): Flow<Response<UserWithPerson>> =
        getSingleUserWithPersonFlow(userId, shouldStream)

    private fun getSingleUserWithPersonFlow(userId: String, shouldStream: Boolean) =
        get


}
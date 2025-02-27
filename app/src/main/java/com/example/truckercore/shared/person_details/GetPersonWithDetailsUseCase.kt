package com.example.truckercore.shared.person_details

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface GetPersonWithDetailsUseCase {

    fun execute(user: User): Flow<Response<PersonWithDetails>>

}
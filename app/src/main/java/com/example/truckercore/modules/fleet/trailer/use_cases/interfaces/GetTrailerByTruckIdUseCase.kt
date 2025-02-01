package com.example.truckercore.modules.fleet.trailer.use_cases.interfaces

import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface GetTrailerByTruckIdUseCase {

    suspend fun execute(user: User, truckId: String): Flow<Response<List<Trailer>>>

}
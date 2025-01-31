package com.example.truckercore.modules.fleet.trailer.use_cases.interfaces

import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

interface GetTrailerByTruckIdUseCase {

    suspend fun execute(user: User, truckId: String): Flow<Response<List<Trailer>>>

}
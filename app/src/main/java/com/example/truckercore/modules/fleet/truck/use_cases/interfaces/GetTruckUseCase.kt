package com.example.truckercore.modules.fleet.truck.use_cases.interfaces

import com.example.truckercore.modules.fleet.truck.entity.Truck
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving a [Truck] entity by its ID.
 */
internal interface GetTruckUseCase {

    fun execute(documentParams: DocumentParameters): Flow<Response<Truck>>

    fun execute(queryParams: QueryParameters): Flow<Response<List<Truck>>>

}
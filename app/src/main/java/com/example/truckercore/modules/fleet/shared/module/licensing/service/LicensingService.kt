package com.example.truckercore.modules.fleet.shared.module.licensing.service

import com.example.truckercore.modules.fleet.shared.module.licensing.configs.LicensingAggregation
import com.example.truckercore.modules.fleet.shared.module.licensing.configs.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

interface LicensingService {

    suspend fun getData(params: DocumentParameters): Flow<Response<Licensing>>

    suspend fun getData(params: QueryParameters): Flow<Response<List<Licensing>>>

    suspend fun getWithAggregateData(
        params: DocumentParameters,
        aggregation: LicensingAggregation
    ): Flow<Response<LicensingWithFile>>

    suspend fun getWithAggregateData(
        params: QueryParameters,
        aggregation: LicensingAggregation
    ): Flow<Response<List<LicensingWithFile>>>

}
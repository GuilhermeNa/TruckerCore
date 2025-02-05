package com.example.truckercore.modules.fleet.shared.module.licensing.service

import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.AggregateLicensingWithFilesUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class LicensingServiceImpl(
    private val getUseCase: GetLicensingUseCase,
    private val aggregateLicensing: AggregateLicensingWithFilesUseCase
) : LicensingService {

    override suspend fun fetchLicensing(params: DocumentParameters): Flow<Response<Licensing>> =
        getUseCase.execute(params.user, params.id)

    override suspend fun fetchLicensing(params: QueryParameters): Flow<Response<List<Licensing>>> =
        getUseCase.execute(params.user, *params.queries)

    override suspend fun fetchLicensingWithFiles(params: DocumentParameters) =
        aggregateLicensing.execute(params.user, params.id)

    override suspend fun fetchLicensingWithFiles(params: QueryParameters) =
        aggregateLicensing.execute(params.user, *params.queries)

}
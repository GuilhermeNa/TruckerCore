package com.example.truckercore.modules.fleet.shared.module.licensing.service

import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.AggregateLicensingWithFilesUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class LicensingServiceImpl(
    private val getLicensing: GetLicensingUseCase,
    private val getLicensingWithFile: AggregateLicensingWithFilesUseCase
) : LicensingService {

    override suspend fun fetchLicensing(documentParam: DocumentParameters): Flow<Response<Licensing>> =
        getLicensing.execute(documentParam)

    override suspend fun fetchLicensing(queryParam: QueryParameters): Flow<Response<List<Licensing>>> =
        getLicensing.execute(queryParam)

    override suspend fun fetchLicensingWithFiles(documentParam: DocumentParameters) =
        getLicensingWithFile.execute(documentParam)

    override suspend fun fetchLicensingWithFiles(queryParam: QueryParameters) =
        getLicensingWithFile.execute(queryParam)

}
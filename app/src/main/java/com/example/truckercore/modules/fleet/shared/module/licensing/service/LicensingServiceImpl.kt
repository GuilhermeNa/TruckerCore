package com.example.truckercore.modules.fleet.shared.module.licensing.service

import com.example.truckercore.shared.abstractions.Service
import com.example.truckercore.infrastructure.util.ExceptionHandler
import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.AggregateLicensingWithFilesUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class LicensingServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val getLicensing: GetLicensingUseCase,
    private val getLicensingWithFile: AggregateLicensingWithFilesUseCase
) : Service(exceptionHandler), LicensingService {

    override fun fetchLicensing(
        documentParam: DocumentParameters
    ): Flow<Response<Licensing>> =
        runSafe { getLicensing.execute(documentParam) }

    override fun fetchLicensing(
        queryParam: QueryParameters
    ): Flow<Response<List<Licensing>>> =
        runSafe { getLicensing.execute(queryParam) }

    override fun fetchLicensingWithFiles(
        documentParam: DocumentParameters
    ): Flow<Response<LicensingWithFile>> =
        runSafe { getLicensingWithFile.execute(documentParam) }

    override fun fetchLicensingWithFiles(
        queryParam: QueryParameters
    ): Flow<Response<List<LicensingWithFile>>> =
        runSafe { getLicensingWithFile.execute(queryParam) }

}
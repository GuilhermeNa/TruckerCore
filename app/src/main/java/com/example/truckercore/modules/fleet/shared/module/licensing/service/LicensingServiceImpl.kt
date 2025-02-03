package com.example.truckercore.modules.fleet.shared.module.licensing.service

import com.example.truckercore.modules.fleet.shared.module.licensing.configs.LicensingAggregation
import com.example.truckercore.modules.fleet.shared.module.licensing.configs.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingByIdUseCase
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.GetStorageFileByParentIdUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class LicensingServiceImpl(
    private val getLicensing: GetLicensingByIdUseCase,
    private val fileUseCase: GetStorageFileByParentIdUseCase
) : LicensingService {

    override suspend fun getData(params: DocumentParameters): Flow<Response<Licensing>> =
        getLicensing.execute(params.user, params.id)


    override suspend fun getData(params: QueryParameters): Flow<Response<List<Licensing>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWithAggregateData(
        params: DocumentParameters,
        aggregation: LicensingAggregation
    ): Flow<Response<LicensingWithFile>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWithAggregateData(
        params: QueryParameters,
        aggregation: LicensingAggregation
    ): Flow<Response<List<LicensingWithFile>>> {
        TODO("Not yet implemented")
    }


}
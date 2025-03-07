package com.example.truckercore.model.shared.modules.personal_data.service

import com.example.truckercore.model.infrastructure.util.ExceptionHandler
import com.example.truckercore.model.shared.abstractions.Service
import com.example.truckercore.model.shared.modules.personal_data.aggregations.PersonalDataWithFile
import com.example.truckercore.model.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataUseCase
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataWithFilesUseCase
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class PersonalDataServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val getPersonalData: GetPersonalDataUseCase,
    private val getPDataWF: GetPersonalDataWithFilesUseCase
) : Service(exceptionHandler), PersonalDataService {

    override fun fetchPersonalData(documentParam: DocumentParameters): Flow<Response<PersonalData>> =
        runSafe { getPersonalData.execute(documentParam) }

    override fun fetchPersonalData(queryParam: QueryParameters): Flow<Response<List<PersonalData>>> =
        runSafe { getPersonalData.execute(queryParam) }

    override fun fetchPersonalDataWithDetails(documentParam: DocumentParameters): Flow<Response<PersonalDataWithFile>> =
        runSafe { getPDataWF.execute(documentParam) }

    override fun fetchPersonalDataWithDetails(queryParam: QueryParameters): Flow<Response<List<PersonalDataWithFile>>> =
        runSafe { getPDataWF.execute(queryParam) }

}
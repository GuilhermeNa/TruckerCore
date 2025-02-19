package com.example.truckercore.shared.modules.personal_data.service

import com.example.truckercore.shared.abstractions.Service
import com.example.truckercore.infrastructure.exceptions.ExceptionHandler
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.AggregatePersonalDataWithFilesUseCase
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class PersonalDataServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val getPersonalData: GetPersonalDataUseCase,
    private val getPersonalDataWithFile: AggregatePersonalDataWithFilesUseCase
) : Service(exceptionHandler), PersonalDataService {

    override fun fetchPersonalData(documentParam: DocumentParameters): Flow<Response<PersonalData>> =
        runSafe { getPersonalData.execute(documentParam) }

    override fun fetchPersonalData(queryParam: QueryParameters): Flow<Response<List<PersonalData>>> =
        runSafe { getPersonalData.execute(queryParam) }

    override fun fetchPersonalDataWithDetails(documentParam: DocumentParameters): Flow<Response<PersonalDataWithFile>> =
        runSafe { getPersonalDataWithFile.execute(documentParam) }

    override fun fetchPersonalDataWithDetails(queryParam: QueryParameters): Flow<Response<List<PersonalDataWithFile>>> =
        runSafe { getPersonalDataWithFile.execute(queryParam) }

}
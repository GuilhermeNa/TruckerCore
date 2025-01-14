package com.example.truckercore.shared.modules.personal_data.repositories.interfaces

import com.example.truckercore.shared.interfaces.Repository
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface PersonalDataRepository : Repository<PersonalDataDto> {

    /**
     * Fetches a list of PersonalData entities by their parent ID.
     *
     * @param parentId The parent identifier used to filter the personal data entities.
     * @return A [Flow] that emits a [Response] containing a list of PersonalDataDto entities.
     * If an error occurs, an [Error] response is emitted instead.
     */
    suspend fun fetchPersonalDataByParentId(parentId: String): Flow<Response<List<PersonalDataDto>>>

}
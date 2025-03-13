package com.example.truckercore.model.modules.notification.repository

import com.example.truckercore.model.modules.notification.dto.NotificationDto
import com.example.truckercore.model.shared.interfaces.Repository
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface NotificationRepository : Repository {

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<Response<NotificationDto>>

    override fun fetchByQuery(queryParams: QueryParameters): Flow<Response<List<NotificationDto>>>

}
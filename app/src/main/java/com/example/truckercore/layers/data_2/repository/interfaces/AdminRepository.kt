package com.example.truckercore.layers.data_2.repository.interfaces

import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.admin.Admin
import kotlinx.coroutines.flow.Flow

interface AdminRepository {

    suspend fun observe(userId: UserID): Flow<DataOutcome<Admin>>

}
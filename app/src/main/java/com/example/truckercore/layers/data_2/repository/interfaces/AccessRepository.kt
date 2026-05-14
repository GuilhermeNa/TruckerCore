package com.example.truckercore.layers.data_2.repository.interfaces

import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.access.Access
import kotlinx.coroutines.flow.Flow

interface AccessRepository {

    fun fetch(userId: UserID): DataOutcome<Access>

    fun observe(userId: UserID): Flow<DataOutcome<Access>>

    fun save(access: Access): OperationOutcome

}
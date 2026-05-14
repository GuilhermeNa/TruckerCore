package com.example.truckercore.layers.data_2.repository.interfaces

import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.domain.base.contracts.EmployeeID
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.user.User
import com.example.truckercore.layers.domain.model.user.UserDraft
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun completeFetch(userID: UserID): DataOutcome<User>

    suspend fun completeFetch(employeeId: EmployeeID): DataOutcome<User>

    fun observe(uid: UID): Flow<DataOutcome<UserDraft>>

}
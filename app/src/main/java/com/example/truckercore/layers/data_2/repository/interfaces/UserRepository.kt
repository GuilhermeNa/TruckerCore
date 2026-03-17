package com.example.truckercore.layers.data_2.repository.interfaces

import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.model.user.UserDraft
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun observe(uid: UID): Flow<DataOutcome<UserDraft>>

}
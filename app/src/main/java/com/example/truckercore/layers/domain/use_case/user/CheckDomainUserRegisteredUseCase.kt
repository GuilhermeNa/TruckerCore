package com.example.truckercore.layers.domain.use_case.user

import com.example.truckercore.core.my_lib.expressions.isSuccess
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.specification.specs_impl.UserSpec
import com.example.truckercore.layers.data.repository.data.DataRepository
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.model.user.UserDraft

class CheckDomainUserRegisteredUseCase(
    private val repository: DataRepository
) {

    suspend operator fun invoke(uid: UID): DataOutcome<Boolean> {
        val outcome = fetchUser(uid)
        val response = outcome.isSuccess()
        return DataOutcome.Success(response)
    }

    private suspend fun fetchUser(uid: UID): DataOutcome<UserDraft> {
        val spec = UserSpec(uid = uid)
        return repository.findOneBy(spec)
    }

}
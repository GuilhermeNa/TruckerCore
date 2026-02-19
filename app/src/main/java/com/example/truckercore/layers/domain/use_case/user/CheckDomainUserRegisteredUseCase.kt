package com.example.truckercore.layers.domain.use_case.user

import com.example.truckercore.core.my_lib.expressions.map
import com.example.truckercore.infra.logger.Logable
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.specification.specs_impl.UserSpec
import com.example.truckercore.layers.data.repository.data.DataRepository
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.model.user.UserDraft

class CheckDomainUserRegisteredUseCase(
    private val repository: DataRepository
) : Logable {

    suspend operator fun invoke(uid: UID): DataOutcome<Boolean> {
        val outcome = fetchUser(uid)
        outcome.logFailure(CHECK_ACCESS_ERROR)
        val response = outcome.toResponse()
        return response
    }

    private suspend fun fetchUser(uid: UID): DataOutcome<List<UserDraft>> {
        val spec = UserSpec(uid = uid)
        return repository.findByFilter(spec)
    }

    private fun DataOutcome<List<UserDraft>>.toResponse() = this.map(
        onSuccess = { DataOutcome.Success(true) },
        onFailure = { DataOutcome.Failure(it) },
        onEmpty = { DataOutcome.Success(false) }
    )

    private companion object {
        private const val CHECK_ACCESS_ERROR =
            "An error occurred while checking if user has domain access."
    }

}
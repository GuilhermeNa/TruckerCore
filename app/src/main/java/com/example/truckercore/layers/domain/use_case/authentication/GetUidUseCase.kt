package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository
import com.example.truckercore.layers.domain.base.ids.UID

interface GetUidUseCase {
    fun invoke(): DataOutcome<UID>
}

class GetUidUseCaseImpl(private val repository: AuthenticationRepository) : GetUidUseCase {

    override fun invoke(): DataOutcome<UID> = repository.getUid()

}
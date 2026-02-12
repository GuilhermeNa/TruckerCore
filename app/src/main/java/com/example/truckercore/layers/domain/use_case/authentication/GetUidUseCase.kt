package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository
import com.example.truckercore.layers.domain.base.ids.UID

class GetUidUseCase(private val repository: AuthenticationRepository) {

    operator fun invoke(): DataOutcome<UID> = repository.getUid()

}
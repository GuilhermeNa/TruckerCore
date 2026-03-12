package com.example.truckercore.layers.domain.use_case.user

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.my_lib.expressions.map
import com.example.truckercore.infra.logger.Logable
import com.example.truckercore.layers.data.base.dto.impl.UserDto
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.specification.specs_impl.UserSpec
import com.example.truckercore.layers.data.repository.data.DataRepository
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.model.user.UserDraft
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveUserDraftUseCase(private val repository: DataRepository) : Logable {

    operator fun invoke(uid: UID): Flow<DataOutcome<UserDraft>> =
        flow(uid).map { outcome ->
            outcome.map(
                onSuccess = ::handleSuccess,
                onFailure = ::handleError,
                onEmpty = { DataOutcome.Empty }
            )
        }

    private fun flow(uid: UID) =
        repository.flowByFilter<UserDto, UserDraft>(UserSpec(uid = uid))

    private fun handleSuccess(data: List<UserDraft>) =
        if (data.size > 1) {
            val error = DomainException.RuleViolation(MULTIPLE_USERS)
            error.logFailure(MULTIPLE_USERS)
            DataOutcome.Failure(error)
        } else {
            DataOutcome.Success(data.first())
        }

    private fun handleError(error: AppException): DataOutcome.Failure {
        error.logFailure(ERROR)
        return DataOutcome.Failure(error)
    }

    private companion object {
        private const val MULTIPLE_USERS = "Multiple users found for the same UID."
        private const val ERROR = "Some error occurred while observing an User Draft."
    }

}
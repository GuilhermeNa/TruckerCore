package com.example.truckercore.layers.domain.use_case._base

import android.util.Log
import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

abstract class UseCase {

    private val classTag = this::class.simpleName

    protected fun failureOutcome(throwable: Throwable, unknownMessage: String) =
        when {
            throwable is AppException -> DataOutcome.Failure(throwable)

            else -> {
                Log.e(classTag, unknownMessage, throwable)
                val message = "$classTag: $unknownMessage"
                DataOutcome.Failure(AppException(message, throwable))
            }
        }

    protected fun toFailureOutcomeFlow(exception: AppException): Flow<DataOutcome.Failure> =
        flowOf(DataOutcome.Failure(exception))

    protected fun ruleViolatedOutcomeFlow(message: String): Flow<DataOutcome.Failure> {
        val errorMsg = "$classTag: $message"
        val error = DomainException.RuleViolation(errorMsg)
        return flowOf(DataOutcome.Failure(error))
    }

    protected fun ruleViolatedOutcome(message: String): DataOutcome.Failure {
        val errorMsg = "$classTag: $message"
        val error = DomainException.RuleViolation(errorMsg)
        return DataOutcome.Failure(error)
    }

}
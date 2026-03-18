package com.example.truckercore.core.my_lib.expressions

import com.example.truckercore.layers.data.base.outcome.DataOutcome
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

fun <T, R> Flow<DataOutcome<T>>.mapOutcome(
    transform: (T) -> R
): Flow<DataOutcome<R>> =
    map { it.mapSuccess(transform) }

/*fun <T> Flow<DataOutcome<T>>.requiredOutcome(): Flow<DataOutcome<T>> = map { it.required
    () }*/

@OptIn(ExperimentalCoroutinesApi::class)
fun <T, R> Flow<DataOutcome<T>>.flatMapOutcome(
    transform: (T) -> Flow<DataOutcome<R>>
): Flow<DataOutcome<R>> =
    flatMapLatest { outcome ->
        when (outcome) {
            is DataOutcome.Success -> transform(outcome.data)
            is DataOutcome.Failure -> flowOf(outcome)
            DataOutcome.Empty -> flowOf(DataOutcome.Empty)
        }
    }
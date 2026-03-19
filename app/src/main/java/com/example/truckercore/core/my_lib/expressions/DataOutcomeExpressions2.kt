package com.example.truckercore.core.my_lib.expressions

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.error.default_errors.EmptyDataException
import com.example.truckercore.layers.data.base.outcome.DataOutcome

fun DataOutcome<*>.isFailure(): Boolean = this is DataOutcome.Failure

fun DataOutcome<*>.isSuccess(): Boolean = this is DataOutcome.Success

fun DataOutcome<*>.isEmpty(): Boolean = this is DataOutcome.Empty

inline fun <T, R> DataOutcome<T>.map(
    transform: (T) -> R
): DataOutcome<R> = when (this) {
    is DataOutcome.Success -> DataOutcome.Success(transform(data))
    is DataOutcome.Failure -> this
    DataOutcome.Empty -> DataOutcome.Empty
}

inline fun <T, R> DataOutcome<T>.fold(
    onSuccess: (T) -> R,
    onFailure: (AppException) -> R,
    onEmpty: () -> R
): R = when (this) {
    is DataOutcome.Success -> onSuccess(data)
    is DataOutcome.Failure -> onFailure(exception)
    DataOutcome.Empty -> onEmpty()
}

inline fun <A, B, R> zip(
    a: DataOutcome<A>,
    b: DataOutcome<B>,
    transform: (A, B) -> R
): DataOutcome<R> = when {
    a is DataOutcome.Failure -> a
    b is DataOutcome.Failure -> b
    a is DataOutcome.Success && b is DataOutcome.Success ->
        DataOutcome.Success(transform(a.data, b.data))

    else -> DataOutcome.Empty
}

inline fun <A, B, C, R> zip(
    a: DataOutcome<A>,
    b: DataOutcome<B>,
    c: DataOutcome<C>,
    transform: (A, B, C) -> R
): DataOutcome<R> = when {
    a is DataOutcome.Failure -> a
    b is DataOutcome.Failure -> b
    c is DataOutcome.Failure -> c

    a is DataOutcome.Success && b is DataOutcome.Success && c is DataOutcome.Success ->
        DataOutcome.Success(transform(a.data, b.data, c.data))

    else -> DataOutcome.Empty
}

fun <T> DataOutcome<T>.required(
    message: String
): DataOutcome<T> = when (this) {
    DataOutcome.Empty -> {
        val error = DomainException.RuleViolation(message)
        DataOutcome.Failure(error)
    }

    else -> this
}

inline fun <T, R> firstSuccess(
    vararg outcomes: DataOutcome<T>,
    transform: (T) -> R
): DataOutcome<R> {
    for (outcome in outcomes) {
        when (outcome) {
            is DataOutcome.Success -> return DataOutcome.Success(transform(outcome.data))
            is DataOutcome.Failure -> return outcome
            else -> {} // continua procurando Success
        }
    }
    return DataOutcome.Empty
}

fun <T> allSuccess(vararg outcomes: DataOutcome<T>) =
    outcomes.all { it.isSuccess() }

fun <T> allEmpty(vararg outcomes: DataOutcome<T>) =
    outcomes.all { it.isEmpty() }

fun <T> allFailure(vararg outcomes: DataOutcome<T>) =
    outcomes.all { it.isFailure() }

//------------------------------

inline fun <T> DataOutcome<T>.onFailure(
    failure: (Throwable) -> Unit
): DataOutcome<T> {
    if (this is DataOutcome.Failure) failure(this.exception)
    return this
}

inline fun <R, T> DataOutcome<T>.onSuccess(
    transform: (T) -> R
): DataOutcome<T> {
    if (this is DataOutcome.Success) transform(data)
    return this
}

inline fun <T, R> DataOutcome<T>.flatMapSuccess(
    transform: (T) -> DataOutcome<R>
): DataOutcome<R> = when (this) {
    is DataOutcome.Success -> transform(data)
    is DataOutcome.Failure -> this
    DataOutcome.Empty -> DataOutcome.Empty
}

/**
 * Returns the data if the [DataOutcome] is [DataOutcome.Success], or `null` otherwise.
 */
fun <T> DataOutcome<T>.getOrNull(): T? = (this as? DataOutcome.Success)?.data

fun <T> DataOutcome<T>.getOrThrow(): T = when (this) {
    is DataOutcome.Success -> data
    is DataOutcome.Failure -> throw exception
    DataOutcome.Empty -> throw EmptyDataException("Value is empty")
}


inline fun <T, R> DataOutcome<T>.foldRequired(
    onSuccess: (T) -> R,
    orElse: (AppException) -> R
): R = when (this) {
    is DataOutcome.Success -> onSuccess(data)
    is DataOutcome.Failure -> orElse(exception)
    DataOutcome.Empty -> orElse(
        DomainException.RuleViolation(
            ("A required value was not provided or could not be found. " +
                    "This operation expected a non-empty result, but received " +
                    "an empty outcome instead [$this].")
        )
    )
}

// Dado é necessario. Mapeia os resultados empty em erros


/*

operator fun invoke(): Flow<DataOutcome<Session>> {
    val uidOutcome = authRepository.getUid()
        .mapEmptyToFailure(EMPTY_UID)

    return when (uidOutcome) {
        is DataOutcome.Failure -> flowOf(uidOutcome)
        is DataOutcome.Success -> {
            userRepository.observe(uidOutcome.data)
                .map { it.mapEmptyToFailure(EMPTY_USER) }
                .flatMapOutcome { user ->

                    combine(
                        accessRepository.observe(user.id)
                            .map { it.mapEmptyToFailure(EMPTY_ACCESS) },

                        combineAdminAndDriverFlows(user.id)
                            .map { it.mapEmptyToFailure(EMPTY_EMPLOYEE) }

                    ) { access, employee ->

                        zip(access, employee) { a, e ->
                            SessionFactory.toEntity(user, a, e)
                        }
                    }
                }
                .catch {
                    Log.e(classTag, MAIN_FLOW_ERROR, it)
                    emit(it.toOutcome())
                }
        }
        DataOutcome.Empty -> flowOf(DataOutcome.Failure(
            DomainException.NotFound(EMPTY_UID)
        ))
    }
}*/

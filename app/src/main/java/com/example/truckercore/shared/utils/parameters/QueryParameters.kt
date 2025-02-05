package com.example.truckercore.shared.utils.parameters

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.expressions.logWarn

class QueryParameters private constructor(
    val user: User,
    val liveObserver: Boolean,
    vararg val queries: QuerySettings
) {

    companion object {
        fun create(user: User) = Builder(user)
    }

    class Builder(val user: User) {

        private var liveObserver: Boolean = false
        private val queries = mutableListOf<QuerySettings>()

        fun setQueries(vararg newQueries: QuerySettings) = apply {
            queries.clear()
            queries.addAll(newQueries)
        }

        fun setLiveObserver(newLiveObserver: Boolean) =
            apply { this.liveObserver = newLiveObserver }

        fun build(): QueryParameters {
            if (queries.isEmpty()) handleEmptyQueriesError()
            return QueryParameters(user, liveObserver, *queries.toTypedArray())
        }

        private fun handleEmptyQueriesError(): Nothing {
            val message = "You must provide at least one query before build a QueryParameter."
            logWarn(javaClass, message)
            throw IllegalArgumentException(message)
        }

    }

}
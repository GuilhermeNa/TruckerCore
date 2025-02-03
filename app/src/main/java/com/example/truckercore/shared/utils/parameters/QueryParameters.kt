package com.example.truckercore.shared.utils.parameters

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.expressions.logWarn

class QueryParameters private constructor(
    val user: User,
    val liveObserver: Boolean,
    val queries: List<QuerySettings>
) {

    companion object {
        fun create(user: User) = Builder(user)
        internal const val EMPTY_QUERY_ERROR =
            "You must provide at least one query before build a QueryParameter."
        internal const val ID_ERROR =
            "ID searches must be performed using DocumentParametersBuilder, not QueryParametersBuilder."
    }

    class Builder(val user: User) {

        private var liveObserver: Boolean = false
        private val queries = mutableListOf<QuerySettings>()

        fun setQueries(newQueries: Array<QuerySettings>) = apply {
            queries.clear()
            queries.addAll(newQueries)
        }

        fun setLiveObserver(newLiveObserver: Boolean) =
            apply { this.liveObserver = newLiveObserver }


        fun build(): QueryParameters {
            if (queries.isEmpty()) handleError(EMPTY_QUERY_ERROR)
            if (queries.any { it.field == Field.ID }) handleError(ID_ERROR)
            return QueryParameters(user, liveObserver, queries)
        }

        private fun handleError(errorMessage: String): Nothing {
            logWarn(javaClass, errorMessage)
            throw IllegalArgumentException(errorMessage)
        }

    }

}
package com.example.truckercore.shared.utils.parameters

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.IllegalQueryParametersException

class QueryParameters private constructor(
    override val user: User,
    override val shouldStream: Boolean,
    vararg val queries: QuerySettings
) : SearchParameters { // TODO investigar uma forma de adicionar o limit, order, direction e etc

    init {
        if (queries.isEmpty()) {
            throw IllegalQueryParametersException(
                "You must provide at least one query before build a QueryParameter."
            )
        }
    }

    companion object {
        fun create(user: User) = Builder(user)
    }

    class Builder(val user: User) {

        private var shouldStream: Boolean = false
        private val queries = mutableListOf<QuerySettings>()

        fun setQueries(vararg newQueries: QuerySettings) = apply {
            queries.clear()
            queries.addAll(newQueries)
        }

        fun setStream(shouldStream: Boolean) = apply { this.shouldStream = shouldStream }

        fun build(): QueryParameters = QueryParameters(user, shouldStream, *queries.toTypedArray())

    }

}
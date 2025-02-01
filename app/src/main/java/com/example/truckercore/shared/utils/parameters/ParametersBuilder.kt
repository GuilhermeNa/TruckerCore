package com.example.truckercore.shared.utils.parameters

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.Aggregation
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.sealeds.SearchCriteria

class ParametersBuilder(private val user: User) {

    private var id: String? = null
    private val queries = mutableListOf<QueryData>()
    private var liveObserver: Boolean = false
    private var aggregation: Aggregation? = null

    fun setId(id: String): ParametersBuilder {
        checkQueriesEmpty()
        this.id = id
        return this
    }

    fun setQueries(vararg newQuery: QueryData): ParametersBuilder {
        checkIdEmpty()
        queries.clear()
        queries.addAll(newQuery)
        return this
    }

    fun setLiveObserver(liveObserver: Boolean): ParametersBuilder {
        this.liveObserver = liveObserver
        return this
    }

    fun setAggregation(aggregation: Aggregation): ParametersBuilder {
        this.aggregation = aggregation
        return this
    }

    fun build(): Parameters {
        validateParameters()

        val criteria = id?.let {
            SearchCriteria.ById(it)
        } ?: SearchCriteria.ByQuery(queries)

        return Parameters(
            user = user,
            criteria = criteria,
            liveObserver = liveObserver,
            aggregation = aggregation
        )
    }

    private fun checkQueriesEmpty() {
        if (queries.isNotEmpty()) {
            val errorMessage =
                "Queries are already set. You cannot set an ID after queries."
            logWarn(javaClass, errorMessage)
            throw IllegalArgumentException(errorMessage)
        }
    }

    private fun checkIdEmpty() {
        if (id != null) {
            val errorMessage =
                "ID is already set. You cannot add queries after setting an ID."
            logWarn(javaClass, errorMessage)
            throw IllegalArgumentException(errorMessage)
        }
    }

    private fun validateParameters() {
        if (id != null && queries.isNotEmpty()) {
            val errorMessage =
                "You must provide only one search format: ID or Queries. Both are set."
            logWarn(javaClass, errorMessage)
            throw IllegalArgumentException(errorMessage)
        }
        if (id == null && queries.isEmpty()) {
            val errorMessage =
                "You must provide either an ID or a list of Queries. Neither was provided."
            logWarn(javaClass, errorMessage)
            throw IllegalArgumentException(errorMessage)
        }
    }

}
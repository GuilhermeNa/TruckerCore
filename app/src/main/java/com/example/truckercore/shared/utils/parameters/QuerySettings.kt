package com.example.truckercore.shared.utils.parameters

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.utils.expressions.logWarn

data class QuerySettings(val field: Field, val type: QueryType, val value: Any) {

    init {
        if (field == Field.ID) {
            val message =
                "ID searches must be performed using DocumentParametersBuilder," +
                        " not QueryParametersBuilder."
            logWarn(
                context = javaClass,
                message = message
            )
            throw IllegalArgumentException(message)
        }

        when (type) {
            QueryType.WHERE_IN -> if (value !is List<*>) {
                val message =
                    "Expecting a list for WHERE_IN search but received: ${value.javaClass.simpleName}."
                logWarn(
                    context = javaClass,
                    message = message
                )
                throw IllegalArgumentException(message)
            }

            QueryType.WHERE_EQUALS -> if (value is List<*>) {
                val message = "Expecting a unique value and got a list."
                logWarn(
                    context = javaClass,
                    message = message
                )
                throw IllegalArgumentException(message)
            }
        }
    }

}
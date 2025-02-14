package com.example.truckercore.shared.utils.parameters

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.errors.InvalidQuerySettingsException

data class QuerySettings(val field: Field, val type: QueryType, val value: Any) {

    init {
        if (field == Field.ID) {
            throw InvalidQuerySettingsException(
                "ID searches must be performed using DocumentParametersBuilder," +
                        " not QueryParametersBuilder."
            )
        }

        when (type) {
            QueryType.WHERE_IN -> if (value !is List<*>) {
                throw InvalidQuerySettingsException(
                    "Expecting a list for WHERE_IN search but received: ${value.javaClass.simpleName}."
                )
            }

            QueryType.WHERE_EQUALS -> if (value is List<*>) {
                throw InvalidQuerySettingsException(
                    "Expecting a unique value and got a list."
                )
            }
        }
    }

}
package com.example.truckercore.model.shared.utils.parameters

import com.example.truckercore.model.configs.enums.Field
import com.example.truckercore.model.shared.enums.QueryType
import com.example.truckercore.model.shared.errors.search_params.InvalidQuerySettingsException

/**
 * Data class that represents the settings for a query, including the field, query type, and the value
 * associated with the query condition.
 *
 * It validates the query settings to ensure that the configurations are correct based on the specified field
 * and query type. If the settings are invalid, an `InvalidQuerySettingsException` is thrown.
 *
 * @param field The field to search on.
 * @param type The type of the query (e.g., WHERE_IN, WHERE_EQUALS).
 * @param value The value associated with the query condition (e.g., list or single value).
 *
 * @throws InvalidQuerySettingsException If the field is `ID` or if the value doesn't match the expected type
 *         for a specific query type.
 *
 */
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
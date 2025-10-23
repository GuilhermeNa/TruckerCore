package com.example.truckercore.layers.data.base.filter.contract

import com.example.truckercore.core.config.enums.Field

/**
 * Represents a filtering condition to be applied in a query.
 *
 * A [Filter] defines a comparison between a [field] and a [value] in a query operation.
 *
 * Filters are intentionally backend-agnostic and must be interpreted by the [DataSourceSpecificationInterpreter].
 * ### Example:
 * ```kotlin
 * val filter = WhereEqual(Field.CATEGORY, "books")
 * ```
 * @property field The field to filter on (e.g., `Field.CATEGORY`).
 * @property value The value to compare the field against.
 */
interface Filter {

    /**
     * The field of the entity to apply the filter on.
     */
    val field: Field

    /**
     * The value to compare the field against.
     */
    val value: Any

}
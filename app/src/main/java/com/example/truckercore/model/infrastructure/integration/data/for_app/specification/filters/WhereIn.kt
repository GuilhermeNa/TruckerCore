package com.example.truckercore.model.infrastructure.integration.data.for_app.specification.filters

import com.example.truckercore.model.configs.enums.Field
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Filter

/**
 * Represents a filter that checks if a field's value is contained within a given list.
 */
data class WhereIn(
    override val field: Field,
    override val value: List<Any>
) : Filter
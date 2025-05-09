package com.example.truckercore.model.infrastructure.integration.data.for_app.specification.filters

import com.example.truckercore.model.configs.enums.Field
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Filter

/**
 * Represents a filter that checks if a field is equal to a specific value.
 */
data class WhereEqual(
    override val field: Field,
    override val value: Any
) : Filter

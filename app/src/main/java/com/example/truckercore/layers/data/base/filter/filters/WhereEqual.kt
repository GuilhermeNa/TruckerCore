package com.example.truckercore.layers.data.base.filter.filters

import com.example.truckercore.core.config.enums.Field
import com.example.truckercore.layers.data.base.filter.contract.Filter

/**
 * Represents a filter that checks if a field is equal to a specific value.
 */
data class WhereEqual(
    override val field: Field,
    override val value: Any
) : Filter

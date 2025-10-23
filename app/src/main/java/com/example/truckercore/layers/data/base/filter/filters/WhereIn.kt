package com.example.truckercore.layers.data.base.filter.filters

import com.example.truckercore.core.config.enums.Field
import com.example.truckercore.layers.data.base.filter.contract.Filter

/**
 * Represents a filter that checks if a field's value is contained within a given list.
 */
data class WhereIn(
    override val field: Field,
    override val value: List<Any>
) : Filter
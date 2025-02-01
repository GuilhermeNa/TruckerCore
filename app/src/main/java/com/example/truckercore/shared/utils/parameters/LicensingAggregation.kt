package com.example.truckercore.shared.utils.parameters

import com.example.truckercore.shared.abstractions.Aggregation

data class LicensingAggregation(
    val licensing: Boolean = false,
    val trailer: Boolean = false,
) : Aggregation()
package com.example.truckercore.modules.fleet.trailer.aggregations

import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.trailer.entity.Trailer

data class TrailerWithDetails(
    val trailer: Trailer,
    val licensingWithFiles: List<LicensingWithFile> = emptyList()
)

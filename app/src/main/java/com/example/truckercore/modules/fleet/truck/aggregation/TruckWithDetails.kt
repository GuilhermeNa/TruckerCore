package com.example.truckercore.modules.fleet.truck.aggregation

import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.trailer.aggregations.TrailerWithDetails
import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.fleet.truck.entity.Truck

data class TruckWithDetails(
    val truck: Truck,
    val trailersWithDetails: List<TrailerWithDetails> = emptyList(),
    val licensingWithFiles: List<LicensingWithFile> = emptyList()
)

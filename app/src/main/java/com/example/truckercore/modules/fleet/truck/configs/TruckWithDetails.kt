package com.example.truckercore.modules.fleet.truck.configs

import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.fleet.truck.entity.Truck

data class TruckWithDetails(
    val truck: Truck,
    val trailers: List<Trailer> = emptyList(),
    val licensing: List<Licensing> = emptyList()
)

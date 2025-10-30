package com.example.truckercore.layers.domain.departments.fleet.objects

import com.example.truckercore.layers.domain.base.others.Plate
import com.example.truckercore.layers.domain.model.driver.Driver
import com.example.truckercore.layers.domain.model.trailer.Trailer
import com.example.truckercore.layers.domain.model.truck.Truck

class Rig(
    private val truck: Truck,
    private val trailers: Set<Trailer>
) {

    fun contains(plate: Plate): Boolean = when {
        truck.plate == plate -> true
        trailers.any { it.plate == plate } -> true
        else -> false
    }

}
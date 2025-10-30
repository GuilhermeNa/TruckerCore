package com.example.truckercore.layers.domain.departments.fleet.objects

import com.example.truckercore.layers.domain.model.driver.Driver
import com.example.truckercore.layers.domain.model.trailer.Trailer
import com.example.truckercore.layers.domain.model.truck.Truck

class Rig(
    val driver: Driver,
    val truck: Truck,
    val trailers: Set<Trailer>
) {


}
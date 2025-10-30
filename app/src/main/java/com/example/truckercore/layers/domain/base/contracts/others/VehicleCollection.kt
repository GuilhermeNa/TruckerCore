package com.example.truckercore.layers.domain.base.contracts.others

import com.example.truckercore.layers.domain.base.abstractions.Vehicle
import com.example.truckercore.layers.domain.base.others.Plate

interface VehicleCollection<T: Vehicle>: DomainCollection<T> {

    fun contains(plate: Plate): Boolean

    fun findBy(plate: Plate): T?

}
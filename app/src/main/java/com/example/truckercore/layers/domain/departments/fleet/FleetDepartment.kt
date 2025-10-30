package com.example.truckercore.layers.domain.departments.fleet

import com.example.truckercore.layers.domain.departments.fleet.collections.RigCollection
import com.example.truckercore.layers.domain.departments.fleet.collections.TrailerCollection
import com.example.truckercore.layers.domain.departments.fleet.collections.TruckCollection

class FleetDepartment {

    private val rigs = RigCollection()

    private val trucks = TruckCollection()

    private val trailers = TrailerCollection()

}
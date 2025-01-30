package com.example.truckercore.modules.fleet.shared.interfaces

import com.example.truckercore.modules.fleet.shared.abstraction.FleetData

interface Fleet {
    val plate: String
    val color: String
    val documents: List<FleetData>
}
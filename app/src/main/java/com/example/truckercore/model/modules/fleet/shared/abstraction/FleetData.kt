package com.example.truckercore.model.modules.fleet.shared.abstraction

import java.time.LocalDateTime

abstract class FleetData(
    open val parentId: String,
    open val emissionDate: LocalDateTime,
)
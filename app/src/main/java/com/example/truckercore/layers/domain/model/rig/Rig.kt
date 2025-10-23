package com.example.truckercore.layers.domain.model.rig

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.enums.PersistenceState
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.RigID
import com.example.truckercore.layers.domain.model.employee.driver.data.Driver
import com.example.truckercore.layers.domain.model.trailer.Trailer
import com.example.truckercore.layers.domain.model.truck.Truck

data class Rig(
    override val id: RigID,
    override val companyId: CompanyID,
    override val persistence: PersistenceState,
    val truck: Truck,
    val driver: Driver? = null,
    val trailers: Set<Trailer> = emptySet()
) : Entity {

    fun assignDriver(newDriver: Driver): Rig {
        if (driver != null) throw DomainException.RuleViolation("")
        return copy(driver = newDriver)
    }

    fun replaceDriver(newDriver: Driver): Rig {
        return copy(driver = newDriver)
    }

    fun attachTrailer(newTrailers: Set<Trailer>) {
        TODO()
    }

    fun detachAllTrailers(): Rig {
        return copy(trailers = emptySet())
    }

    fun isDriverAssigned(): Boolean {
        return driver != null
    }

    fun areTrailersAssigned(): Boolean {
        return trailers.isNotEmpty()
    }

}
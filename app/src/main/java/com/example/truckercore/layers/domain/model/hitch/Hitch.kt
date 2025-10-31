package com.example.truckercore.layers.domain.model.hitch

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.contracts.others.DateRange
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.HitchID
import com.example.truckercore.layers.domain.base.ids.TrailerID
import com.example.truckercore.layers.domain.base.ids.TruckID
import com.example.truckercore.layers.domain.base.others.Period
import com.example.truckercore.layers.domain.model.federal_aet.FederalAet
import com.example.truckercore.layers.domain.model.federal_aet.FederalAetCollection
import com.example.truckercore.layers.domain.model.state_aet.StateAet
import com.example.truckercore.layers.domain.model.state_aet.StateAetCollection

data class Hitch(
    override val id: HitchID,
    override val status: Status,
    override val companyId: CompanyID,
    override val period: Period,
    val truckId: TruckID,
    val trailerIds: Set<TrailerID>
) : Entity, DateRange {

    private val federalAets = FederalAetCollection()

    private val stateAets = StateAetCollection()

    // --------------------------------------------------------
    // Federal Aet
    // --------------------------------------------------------
    fun initFederalAetsFromDatabase(dbAets: List<FederalAet>) {
        dbAets.forEach(::registerFederalAet)
    }

    private fun registerFederalAet(aet: FederalAet) {
        if (federalAets.overlapsAny(aet)) {
            throw DomainException.RuleViolation(FEDERAL_AET_OVERLAPS_ERROR)
        } else federalAets.add(aet)
    }

    fun getCurrentFederalAet(): FederalAet? = federalAets.getCurrent()

    // --------------------------------------------------------
    // State Aet
    // --------------------------------------------------------
    fun initStateAetsFromDatabase(dbAets: List<StateAet>) {
        dbAets.forEach(::registerStateAet)
    }

    private fun registerStateAet(aet: StateAet) {
        if (stateAets.overlapsAny(aet)) {
            throw DomainException.RuleViolation(STATE_AET_OVERLAPS_ERROR)
        } else stateAets.add(aet)
    }

    fun getCurrentStateAet(): StateAet? = stateAets.getCurrent()

    private companion object {

        private const val STATE_AET_OVERLAPS_ERROR =
            "Cannot register State Aet: the provided document period overlaps with an existing State Aet."

        private const val FEDERAL_AET_OVERLAPS_ERROR =
            "Cannot register Federal Aet: the provided document period overlaps with an existing Federal Aet."

    }

}
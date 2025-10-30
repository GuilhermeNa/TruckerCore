package com.example.truckercore.layers.domain.model.truck

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.layers.domain.base.abstractions.Vehicle
import com.example.truckercore.layers.domain.base.enums.Color
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.TruckID
import com.example.truckercore.layers.domain.base.others.Chassi
import com.example.truckercore.layers.domain.base.others.Plate
import com.example.truckercore.layers.domain.base.others.Renavam
import com.example.truckercore.layers.domain.base.others.YearModel
import com.example.truckercore.layers.domain.model.driver_assignment.DriverAssignment
import com.example.truckercore.layers.domain.model.driver_assignment.DriverAssignmentCollection
import com.example.truckercore.layers.domain.model.hitch.Hitch
import com.example.truckercore.layers.domain.model.hitch.HitchCollection
import com.example.truckercore.layers.domain.model.tachograph.Tachograph
import com.example.truckercore.layers.domain.model.tachograph.TachographCollection

data class Truck(
    override val id: TruckID,
    override val companyId: CompanyID,
    override val status: Status,
    override val color: Color,
    override val plate: Plate,
    override val chassi: Chassi?,
    override val renavam: Renavam?,
    override val yearModel: YearModel?
) : Vehicle(color, plate, chassi, renavam, yearModel) {

    private val tachographs = TachographCollection()

    private val driverAssignments = DriverAssignmentCollection()

    private val hitches = HitchCollection()

    // --------------------------------------------------------
    // Tachographs
    // --------------------------------------------------------

    fun initTachographsFromDatabase(dbTachographs: List<Tachograph>) {
        dbTachographs.forEach { registerTachograph(it) }
    }

    private fun registerTachograph(tachograph: Tachograph) {
        if (tachographs.overlapsAny(tachograph)) {
            throw DomainException.RuleViolation(TACHOGRAPH_OVERLAPS_ERROR)
        } else tachographs.add(tachograph)
    }

    fun getCurrentTachograph(): Tachograph? = tachographs.getCurrent()

    fun hasTachographExpiringSoon(withinDays: Long): Boolean =
        tachographs.hasExpiringSoon(withinDays)

    // --------------------------------------------------------
    // DriverAssignments
    // --------------------------------------------------------

    fun initDriverAssignmentsFromDatabase(dbDriverAssignments: List<DriverAssignment>) {
        dbDriverAssignments.forEach { assignDriver(it) }
    }

    private fun assignDriver(driverAssignment: DriverAssignment) {
        if (driverAssignments.overlapsAny(driverAssignment)) {
            throw DomainException.RuleViolation(DRIVER_ASSIGNMENT_OVERLAPS_ERROR)
        } else driverAssignments.add(driverAssignment)
    }

    // --------------------------------------------------------
    // Hitches
    // --------------------------------------------------------

    fun initHitchesFromDatabase(dbHitches: List<Hitch>) {
        dbHitches.forEach { attachHitch(it) }
    }

    private fun attachHitch(hitch: Hitch) {
        if (hitches.overlapsAny(hitch)) {
            throw DomainException.RuleViolation(HITCH_OVERLAPS_ERROR)
        } else hitches.add(hitch)
    }

    fun getCurrentHitch(): Hitch? {
        val current = hitches.getCurrent()

        if (current.size > CURRENT_HITCH_MAX) throw DomainException.RuleViolation(
            CURRENT_HITCH_MAX_ERROR_MSG
        )

        return current.firstOrNull()
    }

    companion object {

        private const val CURRENT_HITCH_MAX = 1

        private const val CURRENT_HITCH_MAX_ERROR_MSG =
            "A truck cannot have more than one active hitch at the same time."

        private const val TACHOGRAPH_OVERLAPS_ERROR =
            "Cannot register Tachograph: the provided document period overlaps with an existing Tachograph."

        private const val DRIVER_ASSIGNMENT_OVERLAPS_ERROR =
            "Cannot register Driver Assignment: the provided assignment period overlaps with an existing Driver Assignment."

        private const val HITCH_OVERLAPS_ERROR =
            "Cannot register Hitch: the provided attachment period overlaps with an existing Hitch."

    }

}
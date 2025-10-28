package com.example.truckercore.layers.domain.model.truck

import com.example.truckercore.layers.domain.base.abstractions.Vehicle
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.TruckID
import com.example.truckercore.layers.domain.base.others.Chassi
import com.example.truckercore.layers.domain.base.others.Color
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

    fun initTachographsFromDatabase(drivers: List<DriverAssignment>) {
        drivers.forEach { driverAssignments.add(it) }
        // ou, se quiser validar também, usar assignDriver(it)
    }

    fun registerTachographs(item: Tachograph) = tachographs.add(item)

    fun getActiveTachograph(): Tachograph? = tachographs.getActive()

    fun hasTachographExpiringSoon(withinDays: Long): Boolean =
        tachographs.hasExpiringSoon(withinDays)

    // --------------------------------------------------------
    // DriverAssignments
    // --------------------------------------------------------

    fun assignDriver(items: List<DriverAssignment>) = driverAssignments.addAll(items)

    fun getActiveDriverAssignment(): DriverAssignment? = driverAssignments.getActive()

    // --------------------------------------------------------
    // Hitches
    // --------------------------------------------------------

    fun attachHitch(items: List<Hitch>) = hitches.addAll(items)

    fun getActiveHitch(): Hitch? = hitches.getActive()

}
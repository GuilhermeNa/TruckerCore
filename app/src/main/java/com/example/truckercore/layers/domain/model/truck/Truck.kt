package com.example.truckercore.layers.domain.model.truck

import com.example.truckercore.layers.domain.base.abstractions.TransportUnit
import com.example.truckercore.layers.domain.base.enums.PersistenceState
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.RigID
import com.example.truckercore.layers.domain.base.ids.TruckID
import com.example.truckercore.layers.domain.base.others.Chassi
import com.example.truckercore.layers.domain.base.others.Color
import com.example.truckercore.layers.domain.base.others.Plate
import com.example.truckercore.layers.domain.base.others.Renavam
import com.example.truckercore.layers.domain.base.others.YearModel
import com.example.truckercore.layers.domain.model.tachograph.Tachograph
import com.example.truckercore.layers.domain.model.tachograph.TachographCollection

data class Truck(
    override val id: TruckID,
    override val rigID: RigID,
    override val companyId: CompanyID,
    override val persistence: PersistenceState,
    override val color: Color,
    override val plate: Plate,
    override val chassi: Chassi?,
    override val renavam: Renavam?,
    override val yearModel: YearModel?
) : TransportUnit(color, plate, chassi, renavam, yearModel) {

    private val tachographCollection = TachographCollection()

    fun addTachograph(crlv: Tachograph) = tachographCollection.add(crlv)

    fun getTachographs(): List<Tachograph> = tachographCollection.toList()

    fun anyActiveTachograph(): Boolean = tachographCollection.anyActive()

    fun getActiveTachograph(): Tachograph? = tachographCollection.getActiveTachograph()

    fun hasTachographExpiringSoon(): Boolean = tachographCollection.hasTachographExpiringSoon()

}
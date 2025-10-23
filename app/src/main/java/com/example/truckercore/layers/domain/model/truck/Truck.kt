package com.example.truckercore.layers.domain.model.truck

import com.example.truckercore.layers.domain.base.abstractions.TransportUnit
import com.example.truckercore.layers.domain.base.enums.PersistenceState
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.RigID
import com.example.truckercore.layers.domain.base.ids.TruckID
import com.example.truckercore.layers.domain.base.others.Chassi
import com.example.truckercore.layers.domain.base.others.Plate
import com.example.truckercore.layers.domain.base.others.Renavam
import com.example.truckercore.layers.domain.base.others.YearModel

data class Truck(
    override val id: TruckID,
    override val rigID: RigID,
    override val companyId: CompanyID,
    override val persistence: PersistenceState,
    override val plate: Plate,
    override val chassi: Chassi?,
    override val renavam: Renavam?,
    override val yearModel: YearModel?
) : TransportUnit(plate, chassi, renavam, yearModel) {

}
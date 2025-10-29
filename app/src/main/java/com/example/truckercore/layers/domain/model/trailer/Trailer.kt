package com.example.truckercore.layers.domain.model.trailer

import com.example.truckercore.layers.domain.base.abstractions.Vehicle
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.RigID
import com.example.truckercore.layers.domain.base.ids.TrailerID
import com.example.truckercore.layers.domain.base.others.Chassi
import com.example.truckercore.layers.domain.base.others.Color
import com.example.truckercore.layers.domain.base.others.Plate
import com.example.truckercore.layers.domain.base.others.Renavam
import com.example.truckercore.layers.domain.base.others.YearModel

data class Trailer(
    override val id: TrailerID,
    override val companyId: CompanyID,
    override val status: Status,
    override val color: Color,
    override val plate: Plate,
    override val chassi: Chassi?,
    override val renavam: Renavam?,
    override val yearModel: YearModel?
) : Vehicle(color, plate, chassi, renavam, yearModel)
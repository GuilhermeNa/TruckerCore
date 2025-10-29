package com.example.truckercore.layers.domain.model.drive_license

import com.example.truckercore.core.my_lib.classes.Url
import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.contracts.others.Document
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.DriveLicenseID
import com.example.truckercore.layers.domain.base.ids.DriverID
import com.example.truckercore.layers.domain.base.others.Period

data class DriveLicense(
    override val id: DriveLicenseID,
    override val companyId: CompanyID,
    override val period: Period,
    override val url: Url,
    override val status: Status,
    val driverID: DriverID
) : Entity, Document

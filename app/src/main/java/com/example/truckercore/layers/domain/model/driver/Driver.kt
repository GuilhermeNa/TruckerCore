package com.example.truckercore.layers.domain.model.driver

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.layers.domain.base.contracts.others.Employee
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.DriverID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.drive_license.DriveLicense
import com.example.truckercore.layers.domain.model.drive_license.DriveLicenseCollection

data class Driver(
    override val id: DriverID,
    override val companyId: CompanyID,
    override val userId: UserID? = null,
    override val status: Status,
    override val name: Name,
    override val email: Email,
) : Employee {

    private val licenseCollection = DriveLicenseCollection()

    fun addLicense(license: DriveLicense) =
        licenseCollection.add(license)

    fun getActiveLicense(): DriveLicense? =
        licenseCollection.getActive()

    fun hasLicenseExpiringSoon(): Boolean =
        licenseCollection.hasExpiringSoon()

}
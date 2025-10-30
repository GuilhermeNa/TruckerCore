package com.example.truckercore.layers.domain.model.driver

import com.example.truckercore.core.error.DomainException
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

    private val licenses = DriveLicenseCollection()

    fun initLicensesFromDatabase(dbLicenses: List<DriveLicense>) {
        dbLicenses.forEach { registerLicense(it) }
    }

    fun registerLicense(license: DriveLicense) {
        if (licenses.overlapsAny(license)) {
            throw DomainException.RuleViolation(LICENSE_OVERLAPS_ERROR)
        } else licenses.add(license)
    }

    fun getActiveLicense(): DriveLicense? = licenses.getActive()

    fun hasLicenseExpiringSoon(withinDays: Long): Boolean = licenses.hasExpiringSoon(withinDays)

    companion object {
        private const val LICENSE_OVERLAPS_ERROR =
            "Cannot register License: the provided document period overlaps with an existing License."
    }

}


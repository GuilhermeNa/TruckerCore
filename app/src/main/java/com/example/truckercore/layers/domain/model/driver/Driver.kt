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

/**
 * Represents a driver employee within the company.
 *
 * A Driver manages one or more driving licenses, ensuring that
 * license validity periods do not overlap.
 */
data class Driver(
    override val id: DriverID,
    override val companyId: CompanyID,
    override val userId: UserID? = null,
    override val status: Status,
    override val name: Name,
    override val email: Email
) : Employee {

    /**
     * Collection of driving licenses associated with the driver.
     * Responsible for enforcing license period rules.
     */
    private val licenses = DriveLicenseCollection()

    /**
     * Initializes the driver's licenses from persisted data.
     *
     * This method should typically be called when rehydrating
     * the aggregate from the database.
     */
    fun initLicensesFromDatabase(dbLicenses: List<DriveLicense>) {
        dbLicenses.forEach { registerLicense(it) }
    }

    /**
     * Registers a new driving license for the driver.
     *
     * @throws DomainException.RuleViolation if the license period
     * overlaps with an existing registered license.
     */
    fun registerLicense(license: DriveLicense) {
        if (licenses.overlapsAny(license)) {
            throw DomainException.RuleViolation(LICENSE_OVERLAPS_ERROR)
        } else {
            licenses.add(license)
        }
    }

    /**
     * Returns the currently active driving license, if any.
     */
    fun getActiveLicense(): DriveLicense? = licenses.getCurrent()

    /**
     * Indicates whether the driver has a license that will expire
     * within the given number of days.
     */
    fun hasLicenseExpiringSoon(withinDays: Long): Boolean =
        licenses.hasExpiringSoon(withinDays)

    companion object {
        private const val LICENSE_OVERLAPS_ERROR =
            "Cannot register License: the provided document period overlaps with an existing License."
    }

}


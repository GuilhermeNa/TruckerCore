package com.example.truckercore.business_admin.layers.domain.use_case.admin

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.my_lib.expressions.zip
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data_2.cache.SessionCache
import com.example.truckercore.layers.data_2.repository.interfaces.AdminRepository
import com.example.truckercore.layers.data_2.repository.interfaces.DriverRepository
import com.example.truckercore.layers.domain.departments.hr.HrDepartment
import com.example.truckercore.layers.domain.model.access.Role
import com.example.truckercore.layers.domain.model.admin.Admin
import com.example.truckercore.layers.domain.model.driver.Driver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

interface GetHrDepartmentUseCase {
    fun observe(): Flow<DataOutcome<HrDepartment>>
}

class GetHrDepartmentUseCaseImpl(
    private val adminRepository: AdminRepository,
    private val driverRepository: DriverRepository
) : GetHrDepartmentUseCase {

    override fun observe(): Flow<DataOutcome<HrDepartment>> {

        // 1: Check if user has permission
        // to get employee collection
        if (!userHasPermission()) {
            return unauthorizedFlow()
        }

        // 2: Retreat companyID from cache
        val companyId = SessionCache.companyID

        // 3: Combine Admin and Driver flows into
        // correct outcome(EmployeeCollection)
        return combine(
            adminRepository.observeList(companyId),
            driverRepository.observeList(companyId),
        ) { adminOutcome, driverOutcome ->
            zip(adminOutcome, driverOutcome, ::hrDepartmentFactory)
        }

    }

    private fun hrDepartmentFactory(
        admins: List<Admin>?,
        drivers: List<Driver>?
    ): HrDepartment {
        val hr = HrDepartment()

        val employees = listOfNotNull(admins, drivers).flatten().toHashSet()
        hr.initFromDatabase(employees)

        return hr
    }

    private fun unauthorizedFlow() = flowOf(
        DataOutcome.Failure(
            DomainException.UnauthorizedAccess(
                "User with role [${SessionCache.role}] " +
                        "cannot search for employees list"
            )
        )
    )

    private fun userHasPermission(): Boolean {
        return SessionCache.role == Role.ADMIN || SessionCache.role == Role.STAFF
    }

}
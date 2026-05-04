package com.example.truckercore.business_admin.layers.domain.use_case.employee

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.my_lib.expressions.zip
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data_2.cache.SessionCache
import com.example.truckercore.layers.data_2.repository.interfaces.AdminRepository
import com.example.truckercore.layers.data_2.repository.interfaces.DriverRepository
import com.example.truckercore.layers.domain.departments.hr.EmployeeCollection
import com.example.truckercore.layers.domain.model.access.Role
import com.example.truckercore.layers.domain.model.admin.Admin
import com.example.truckercore.layers.domain.model.driver.Driver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

interface GetEmployeesUseCase {

    fun observe(): Flow<DataOutcome<EmployeeCollection>>

}


class GetEmployeesUseCaseImpl(
    private val adminRepository: AdminRepository,
    private val driverRepository: DriverRepository
) : GetEmployeesUseCase {

    override fun observe(): Flow<DataOutcome<EmployeeCollection>> {

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
            zip(adminOutcome, driverOutcome, ::collectionFactory)
        }

    }

    private fun collectionFactory(
        admins: List<Admin>?,
        drivers: List<Driver>?
    ): EmployeeCollection {
        val collection = EmployeeCollection()

        admins?.let { collection.addAll(it) }
        drivers?.let { collection.addAll(it) }

        return collection
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
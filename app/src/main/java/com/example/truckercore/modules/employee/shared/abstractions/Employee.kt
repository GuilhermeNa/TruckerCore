package com.example.truckercore.modules.employee.shared.abstractions

import com.example.truckercore.modules.employee.shared.enums.EmployeeStatus
import com.example.truckercore.modules.employee.shared.modules.benefits.entities.EmployeeBenefits
import com.example.truckercore.modules.employee.shared.modules.compensation.entities.Compensation
import com.example.truckercore.modules.employee.shared.modules.employee_contract.entities.EmployeeContract
import com.example.truckercore.shared.abstractions.Person

/**
 * Abstract class representing an Employee entity in the system.
 *
 * @param employeeStatus The status of the driver's employment, represented by an [EmployeeStatus].
 * @param benefits Optional field containing the driver's employee benefits, represented by [EmployeeBenefits]. This can be null.
 * @param compensation Optional field containing the driver's compensation information, represented by [Compensation]. This can be null.
 * @param contractInfo Optional field containing the driver's contract information, represented by [EmployeeContract]. This can be null.
 */
abstract class Employee(
    open val employeeStatus: EmployeeStatus,
    // open val benefits: EmployeeBenefits?,
    // open val compensation: Compensation?,
    // open val contractInfo: EmployeeContract?
) : Person()
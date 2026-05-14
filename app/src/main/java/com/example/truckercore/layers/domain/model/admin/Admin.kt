package com.example.truckercore.layers.domain.model.admin

import com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.view_model.EmployeesState
import com.example.truckercore.core.my_lib.classes.Cpf
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.layers.domain.base.contracts.Employee
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.AdminID
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.drive_license.DriveLicense
import com.example.truckercore.layers.domain.model.user.User

/**
 * Represents an administrative employee.
 *
 * An Admin is a specialized type of Employee with a direct association
 * to a [User] account.
 */
data class Admin(
    override val id: AdminID,
    override val companyId: CompanyID,
    override val status: Status,
    override val name: Name,
    override val email: Email,
    override val userId: UserID?,
    override val state: EmployeesState,
    override val cpf: Cpf,

    override val cnh: DriveLicense? = null,

) : Employee {

    override fun position(): String {
        return "Admin"
    }

}
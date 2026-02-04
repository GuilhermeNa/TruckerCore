package com.example.truckercore.layers.domain.model.admin

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.layers.domain.base.contracts.others.Employee
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.AdminID
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UserID
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
    override val userId: UserID?
) : Employee
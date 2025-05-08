package com.example.truckercore.model.modules.aggregation.system_access.factory

import com.example.truckercore.model.infrastructure.security.data.collections.ValidKeysRegistry
import com.example.truckercore.model.modules._contracts.ID
import com.example.truckercore.model.modules.aggregation.system_access.data.SystemAccessForm
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.company.data.Key
import com.example.truckercore.model.modules.company.factory.CompanyFactory
import com.example.truckercore.model.modules.company.factory.CompanyForm
import com.example.truckercore.model.modules.employee._shared.EmployeeFactory
import com.example.truckercore.model.modules.employee._shared.EmployeeForm
import com.example.truckercore.model.modules.user.data.UserID
import com.example.truckercore.model.modules.user.factory.UserFactory
import com.example.truckercore.model.modules.user.factory.UserForm

object SystemAccessFactory {

    operator fun invoke(form: SystemAccessForm) {

        val companyId = CompanyID(ID.generate())

        // Employee
        val employeeForm = getEmployeeForm(form, companyId)
        val employeeDto = EmployeeFactory(employeeForm)

        // User
        val userForm = getUserForm(form, companyId)
        val userDto = UserFactory(userForm)
        val userId = UserID(userDto.id ?: throw NullPointerException())

        // Company
        val companyForm = getCompanyForm(userId)
        val companyDto = CompanyFactory(companyForm)

    }

    private fun getUserForm(form: SystemAccessForm, companyId: CompanyID): UserForm {
        return UserForm(
            companyId = companyId,
            uid = form.uid,
            role = form.role
        )
    }

    private fun getEmployeeForm(form: SystemAccessForm, companyId: CompanyID): EmployeeForm {
        return EmployeeForm(
            companyId = companyId,
            name = form.name,
            email = form.email,
            role = form.role
        )
    }

    private fun getCompanyForm(userId: UserID): CompanyForm {
        val key = Key(userId.value)
        val validKeys = ValidKeysRegistry(setOf(key))
        return CompanyForm(validKeys)
    }

}
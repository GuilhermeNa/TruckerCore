package com.example.truckercore.model.modules.employee._contracts

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.Dto

interface EmployeeDto: Dto {
    val name: String?
    val email: String?
}
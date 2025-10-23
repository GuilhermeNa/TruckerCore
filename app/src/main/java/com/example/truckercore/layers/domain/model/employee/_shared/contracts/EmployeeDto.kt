package com.example.truckercore.layers.domain.model.employee._shared.contracts

import com.example.truckercore.layers.data.base.dto.contracts.Dto

interface EmployeeDto: Dto {
    val name: String?
    val email: String?
}
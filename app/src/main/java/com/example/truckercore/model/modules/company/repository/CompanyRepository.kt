package com.example.truckercore.model.modules.company.repository

import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.Instruction

interface CompanyRepository {

    fun createCompany(deque: ArrayDeque<Instruction>)

}
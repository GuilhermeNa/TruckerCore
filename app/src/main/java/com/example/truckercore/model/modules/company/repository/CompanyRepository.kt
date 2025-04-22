package com.example.truckercore.model.modules.company.repository

import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.Instruction

interface CompanyRepository {

    fun createCompany(deque: ArrayDeque<Instruction>)

}
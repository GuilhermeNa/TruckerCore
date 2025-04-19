package com.example.truckercore.model.modules.company.repository

import com.example.truckercore.model.infrastructure.integration._instruction.for_app.Instruction

interface CompanyRepository {

    fun createCompany(deque: ArrayDeque<Instruction>)

}
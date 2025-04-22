package com.example.truckercore.model.modules.company.repository

import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration.writer.for_api.InstructionExecutor

class CompanyRepositoryImpl(
    private val executor: InstructionExecutor,
    private val dataSource: DataSource
) : CompanyRepository {

    override fun createCompany(deque: ArrayDeque<Instruction>) {
        TODO("Not yet implemented")
    }

}
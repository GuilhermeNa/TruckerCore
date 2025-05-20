package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository

import com.example.truckercore.model.errors.infra.InfraException

class InstructionExecutorErrorFactory {

    operator fun invoke(e: Exception): InfraException.WriterError {


        TODO()
    }

}
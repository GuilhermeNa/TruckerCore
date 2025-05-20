package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.instructions

import com.example.truckercore.model.configs.collections.CollectionResolver
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.contracts.Instruction
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.exceptions.InstructionException

data class Update(val data: BaseDto) : Instruction {

    override fun getId(): String = data.id ?: throw InstructionException(ERROR_MSG)

    override fun getCollection(): String = CollectionResolver(data).name

    companion object {
        private const val ERROR_MSG = "ID cannot be null for an update instruction."
    }

}
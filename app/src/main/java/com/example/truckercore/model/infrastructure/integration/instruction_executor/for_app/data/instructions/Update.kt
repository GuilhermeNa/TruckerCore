package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.instructions

import com.example.truckercore.model.configs.collections.CollectionResolver
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.contracts.Instruction
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.exceptions.InvalidInstructionException

data class Update(val data: BaseDto) : Instruction {

    override fun getId(): String = data.id ?: throw InvalidInstructionException()

    override fun getCollection(): String = CollectionResolver(data).name

}
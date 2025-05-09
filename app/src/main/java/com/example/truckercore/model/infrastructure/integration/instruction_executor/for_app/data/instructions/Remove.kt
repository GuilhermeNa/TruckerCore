package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.instructions

import com.example.truckercore.model.configs.collections.CollectionResolver
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.contracts.Instruction
import com.example.truckercore.model.modules._contracts.ID

data class Remove(private val id: ID) : Instruction {

    override fun getId(): String = id.value

    override fun getCollection(): String = CollectionResolver(id).name

}
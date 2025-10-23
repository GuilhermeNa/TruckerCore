package com.example.truckercore.layers.data.base.instruction.for_app_impl

import com.example.truckercore.core.config.collections.CollectionResolver
import com.example.truckercore.core.error.InfraException
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.infra.instruction.base.Instruction

data class Update(val data: BaseDto) : Instruction {

    override fun getId(): String = data.id ?: throw InfraException.Instruction(
        "ID cannot be null for an update instruction."
    )

    override fun getCollection(): String = CollectionResolver(data).name

}
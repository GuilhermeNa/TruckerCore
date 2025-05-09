package com.example.truckercore._test_data_provider.fake_objects

import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.contracts.Instruction

data class FakeInstruction(
    val collection: Collection
): Instruction {
    override fun getId(): String {
        TODO("Not yet implemented")
    }

    override fun getCollection(): String {
        TODO("Not yet implemented")
    }
}
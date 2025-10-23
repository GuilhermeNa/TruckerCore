package com.example.truckercore._test_data_provider.fake_objects

import com.example.truckercore.data.infrastructure.repository.writer.contracts.Instruction

data class FakeInstruction(
    val collection: com.example.truckercore.core.config.collections.AppCollection
): Instruction {
    override fun getId(): String {
        TODO("Not yet implemented")
    }

    override fun getCollection(): String {
        TODO("Not yet implemented")
    }
}
package com.example.truckercore._test_data_provider.fake_objects

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.InstructionTag

data class FakeInstruction(
    override val instructionTag: InstructionTag,
    override val collection: Collection
): Instruction
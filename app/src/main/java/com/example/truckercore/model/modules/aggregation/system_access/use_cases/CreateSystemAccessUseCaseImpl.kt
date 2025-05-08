package com.example.truckercore.model.modules.aggregation.system_access.use_cases

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.InstructionTag
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.types.Put
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.types.PutLazy
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository.InstructionExecutorRepository
import com.example.truckercore.model.modules.company.data.CompanyDto
import com.example.truckercore.model.modules.employee.admin.data.AdminDto
import com.example.truckercore.model.modules.user.data.UserDto
import com.example.truckercore.model.shared.utils.sealeds.AppResult

class CreateSystemAccessUseCaseImpl(
    private val instructionExecutor: InstructionExecutorRepository
) : CreateNewSystemAccessUseCase {

    private val companyTag = InstructionTag("companyTag")
    private val userTag = InstructionTag("userTag")
    private val personTag = InstructionTag("personTag")

    private val companyDto = CompanyDto()
    private val userDto = UserDto()
    private val personDto = AdminDto()

    override suspend fun invoke(): AppResult<Unit> {
        val deque = ArrayDeque<Instruction>()

        Put(
            instructionTag = companyTag,
            collection = Collection.COMPANY,
            data = companyDto
        ).let { deque.add(it) }

        PutLazy(
            instructionTag = userTag,
            collection = Collection.USER,
            referenceIdFromTag = listOf(companyTag),
            lazyData = { map ->
                val value = map[companyTag]
                userDto.copy(id = value)
            }
        ).let { deque.add(it) }

        PutLazy(
            instructionTag = personTag,
            collection = Collection.ADMIN,
            referenceIdFromTag = listOf(userTag),
            lazyData = { map ->
                val value = map[userTag]
                personDto.copy(id = value)
            }
        ).let { deque.add(it) }

        return instructionExecutor.invoke(deque)
    }

}
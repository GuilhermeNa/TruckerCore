package com.example.truckercore.model.modules.aggregation.system_access.use_cases

import com.example.truckercore.model.errors.AppExceptionOld
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.instructions.Put
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.collections.InstructionDeque
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository.InstructionExecutorRepository
import com.example.truckercore.model.modules.aggregation.system_access.app_errors.SystemAccessAppException
import com.example.truckercore.model.modules.aggregation.system_access.app_errors.error_codes.NewSystemAccessErrCode
import com.example.truckercore.model.modules.aggregation.system_access.factory.SystemAccessFactory
import com.example.truckercore.model.modules.aggregation.system_access.factory.SystemAccessForm
import com.example.truckercore.model.modules.aggregation.system_access.factory.SystemAccessResult
import com.example.truckercore.model.shared.utils.sealeds.AppResult

class CreateSystemAccessUseCaseImpl(
    private val instructionExecutor: InstructionExecutorRepository
) : CreateNewSystemAccessUseCase {

    override suspend fun invoke(form: SystemAccessForm): AppResult<Unit> {
        // Create dto objects
        val factoryResult = try {
            SystemAccessFactory(form)
        } catch (e: Exception) {
            return AppResult.Error(getAppException(e))
        }

        // Create instructions
        val deque = getDeque(factoryResult)

        // Execute instructions and return result
        return instructionExecutor(deque)
    }

    private fun getAppException(e: Exception): AppExceptionOld {
        val appErr = NewSystemAccessErrCode.Factory
        return SystemAccessAppException(
            message = appErr.userMessage,
            cause = e,
            errorCode = appErr
        )
    }

    private fun getDeque(factoryResult: SystemAccessResult): InstructionDeque {
        return InstructionDeque().apply {
            addInstruction(
                Put(factoryResult.companyDto),
                Put(factoryResult.userDto),
                Put(factoryResult.employeeDto)
            )
        }
    }

}
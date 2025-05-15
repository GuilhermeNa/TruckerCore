package com.example.truckercore.model.modules.aggregation.system_access.use_cases.implementations

import com.example.truckercore.model.errors.exceptions.AppException
import com.example.truckercore.model.errors.exceptions.technical.TechnicalException
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.collections.InstructionDeque
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.instructions.Put
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository.InstructionExecutorRepository
import com.example.truckercore.model.modules.aggregation.system_access.factory.SystemAccessFactory
import com.example.truckercore.model.modules.aggregation.system_access.factory.SystemAccessForm
import com.example.truckercore.model.modules.aggregation.system_access.factory.SystemAccessResult
import com.example.truckercore.model.modules.company.mapper.CompanyMapper
import com.example.truckercore.model.modules.employee._shared.EmployeeMapper
import com.example.truckercore.model.modules.user.mapper.UserMapper
import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore.model.modules.aggregation.system_access.use_cases.interfaces.CreateNewSystemAccessUseCase

class CreateSystemAccessUseCaseImpl(
    private val instructionExecutor: InstructionExecutorRepository
) : CreateNewSystemAccessUseCase {

    override suspend fun invoke(form: SystemAccessForm): AppResult<Unit> =
        try {
            // Create objects related do system access
            val factoryResult = SystemAccessFactory(form)

            // Create instructions
            val deque = getDeque(factoryResult)

            // Execute instructions and return result
            instructionExecutor(deque)

        } catch (e: AppException) {
            AppResult.Error(e)
        } catch (e: Exception) {
            val error = TechnicalException.Unknown(
                "$UNEXPECTED_ERROR_MESSAGE $form", e
            )
            AppResult.Error(error)
        }

    private fun getDeque(factoryResult: SystemAccessResult): InstructionDeque {
        val companyDto = CompanyMapper.toDto(factoryResult.company)
        val userDto = UserMapper.toDto(factoryResult.user)
        val employeeDto = EmployeeMapper.toDto(factoryResult.employee)

        val companyPut = Put(companyDto)
        val userPut = Put(userDto)
        val employeePut = Put(employeeDto)

        return InstructionDeque().apply {
            addInstruction(companyPut, userPut, employeePut)
        }
    }

    companion object {
        private const val UNEXPECTED_ERROR_MESSAGE =
            "An unexpected error occurred while creating system access."
    }

}
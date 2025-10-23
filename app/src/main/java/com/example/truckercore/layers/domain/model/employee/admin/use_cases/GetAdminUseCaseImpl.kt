package com.example.truckercore.layers.domain.model.employee.admin.use_cases

import com.example.truckercore.data.infrastructure.repository.data.contracts.DataRepository
import com.example.truckercore.data.modules.employee.admin.data.Admin
import com.example.truckercore.data.modules.employee.admin.data.AdminDto
import com.example.truckercore.data.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.data.shared.outcome.data.DataOutcome
import com.example.truckercore.core.expressions.getOrElse
import com.example.truckercore.core.expressions.handleErrorResponse

class GetAdminUseCaseImpl(
    private val dataRepository: DataRepository
) : GetAdminUseCase {

    override suspend fun invoke(spec: com.example.truckercore.domain.model.employee.admin.specification.AdminSpec): DataOutcome<Admin> =
        try {
            dataRepository.findOneBy(spec)
                .getOrElse { unsuccessful -> return unsuccessful }
                .let { dto -> getSuccessResponse(dto) }

        } catch (e: Exception) {
            e.handleErrorResponse("$UNKNOWN_ERR_MSG $spec")
        }

    private fun getSuccessResponse(dto: AdminDto): DataOutcome.Success<Admin> {
        val admin = AdminMapper.toEntity(dto)
        return DataOutcome.Success(admin)
    }

    companion object {
        private const val UNKNOWN_ERR_MSG = "Unknown error occurred while fetching an Admin."
    }

}
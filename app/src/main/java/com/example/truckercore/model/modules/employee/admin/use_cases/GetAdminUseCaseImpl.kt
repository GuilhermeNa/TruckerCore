package com.example.truckercore.model.modules.employee.admin.use_cases

import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.modules.employee.admin.data.Admin
import com.example.truckercore.model.modules.employee.admin.data.AdminDto
import com.example.truckercore.model.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.model.modules.employee.admin.specification.AdminSpec
import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore._utils.expressions.getOrElse
import com.example.truckercore._utils.expressions.handleErrorResponse

class GetAdminUseCaseImpl(
    private val dataRepository: DataRepository
) : GetAdminUseCase {

    override suspend fun invoke(spec: AdminSpec): AppResponse<Admin> =
        try {
            dataRepository.findOneBy(spec)
                .getOrElse { unsuccessful -> return unsuccessful }
                .let { dto -> getSuccessResponse(dto) }

        } catch (e: Exception) {
            e.handleErrorResponse("$UNKNOWN_ERR_MSG $spec")
        }

    private fun getSuccessResponse(dto: AdminDto): AppResponse.Success<Admin> {
        val admin = AdminMapper.toEntity(dto)
        return AppResponse.Success(admin)
    }

    companion object {
        private const val UNKNOWN_ERR_MSG = "Unknown error occurred while fetching an Admin."
    }

}
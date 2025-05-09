package com.example.truckercore.model.modules.employee.admin.use_cases

import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification
import com.example.truckercore.model.modules.employee.admin.AdminMapper
import com.example.truckercore.model.modules.employee.admin.data.Admin
import com.example.truckercore.model.modules.employee.admin.data.AdminDto
import com.example.truckercore.model.modules.employee.admin.specification.AdminSpec
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.mapAppResponse

class GetAdminUseCaseImpl(
    private val dataRepository: DataRepository
) : GetAdminUseCase {

    override suspend fun invoke(spec: AdminSpec): AppResponse<Admin> {
        return dataRepository.findOneBy(spec).mapAppResponse(
            onSuccess = { getSuccessResponse(it) },
            onEmpty = { AppResponse.Empty },
            onError = { AppResponse.Error(it) }
        )
    }

    private fun getSuccessResponse(dto: AdminDto): AppResponse.Success<Admin> {
        val admin = AdminMapper.toEntity(dto)
        return AppResponse.Success(admin)
    }

}
package com.example.truckercore.model.modules.vip.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.vip.dto.VipDto
import com.example.truckercore.model.modules.vip.entity.Vip
import com.example.truckercore.model.modules.vip.mapper.VipMapper
import com.example.truckercore.model.modules.vip.repository.VipRepository
import com.example.truckercore.model.modules.vip.use_cases.interfaces.GetVipUseCase
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetVipUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: VipRepository,
    private val validatorService: ValidatorService,
    private val mapper: VipMapper
) : UseCase(permissionService), GetVipUseCase {

    override fun execute(queryParams: QueryParameters): Flow<AppResponse<List<Vip>>> =
        with(queryParams) {
            user.runIfPermitted { getVipListFlow(this) }
        }

    private fun getVipListFlow(queryParams: QueryParameters): Flow<AppResponse<List<Vip>>> =
        repository.fetchByQuery(queryParams).map { response ->
            if (response is AppResponse.Success) {
                val result = response.data.map { validateAndMapToEntity(it) }
                AppResponse.Success(result)
            } else AppResponse.Empty
        }

    private fun validateAndMapToEntity(dto: VipDto): Vip {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

}
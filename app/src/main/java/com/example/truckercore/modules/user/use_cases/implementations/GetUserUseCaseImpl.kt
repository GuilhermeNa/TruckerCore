package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.interfaces.GetUserUseCase
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetUserUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: UserRepository,
    private val validatorService: ValidatorService,
    private val mapper: UserMapper
) : UseCase(permissionService), GetUserUseCase {

    override fun execute(documentParams: DocumentParameters): Flow<Response<User>> =
        with(documentParams) {
            user.runIfPermitted { getMappedUserFlow(documentParams) }
        }

    private fun getMappedUserFlow(documentParams: DocumentParameters): Flow<Response<User>> =
        repository.fetchByDocument(documentParams).map { response ->
            if (response is Response.Success) {
                Response.Success(validateAndMapToEntity(response.data))
            } else Response.Empty
        }

    private fun validateAndMapToEntity(dto: UserDto): User {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters): Flow<Response<List<User>>> =
        with(queryParams) {
            user.runIfPermitted { getMappedLicensingListFlow(queryParams) }
        }

    private fun getMappedLicensingListFlow(queryParams: QueryParameters): Flow<Response<List<User>>> =
        repository.fetchByQuery(queryParams).map { response ->
            if (response is Response.Success) {
                Response.Success(response.data.map { validateAndMapToEntity(it) })
            } else Response.Empty
        }

}
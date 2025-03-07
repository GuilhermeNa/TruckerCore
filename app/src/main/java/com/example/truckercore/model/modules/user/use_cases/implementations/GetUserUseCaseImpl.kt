package com.example.truckercore.model.modules.user.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.user.dto.UserDto
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.modules.user.mapper.UserMapper
import com.example.truckercore.model.modules.user.repository.UserRepository
import com.example.truckercore.model.modules.user.use_cases.interfaces.GetUserUseCase
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetUserUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: UserRepository,
    private val validatorService: ValidatorService,
    private val mapper: UserMapper
) : UseCase(permissionService), GetUserUseCase {

    override fun execute(firebaseUid: String, shouldStream: Boolean): Flow<Response<User>> =
        repository.fetchLoggedUser(firebaseUid, shouldStream).map { response ->
            if (response is Response.Success) {
                val result = validateAndMapToEntity(response.data)
                Response.Success(result)
            } else return@map Response.Empty
        }

    private fun validateAndMapToEntity(dto: UserDto): User {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

    //----------------------------------------------------------------------------------------------

    override fun execute(documentParams: DocumentParameters): Flow<Response<User>> =
        with(documentParams) {
            user.runIfPermitted { getMappedUserFlow(documentParams) }
        }

    private fun getMappedUserFlow(documentParams: DocumentParameters): Flow<Response<User>> =
        repository.fetchByDocument(documentParams).map { response ->
            if (response is Response.Success) {
                val result = validateAndMapToEntity(response.data)
                Response.Success(result)
            } else Response.Empty
        }

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters): Flow<Response<List<User>>> =
        with(queryParams) {
            user.runIfPermitted { getMappedLicensingListFlow(queryParams) }
        }

    private fun getMappedLicensingListFlow(queryParams: QueryParameters): Flow<Response<List<User>>> =
        repository.fetchByQuery(queryParams).map { response ->
            if (response is Response.Success) {
                val result = response.data.map { validateAndMapToEntity(it) }
                Response.Success(result)
            } else Response.Empty
        }

}
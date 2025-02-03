package com.example.truckercore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepositoryImpl
import com.example.truckercore.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.CreateBusinessCentralUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.DeleteBusinessCentralUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.GetBusinessCentralByIdUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.UpdateBusinessCentralUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingByParentIdsUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingByQuery
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.GetTruckByIdUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Named

internal class MyViewModel(
    private val useCase: GetLicensingByQuery
) : ViewModel() {

    private fun entity() = BusinessCentral(
        businessCentralId = "",
        id = "KX1GZjhMEPAbny7Hasdvs",
        lastModifierId = "campoatualizado222",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED
    )

    private fun user() = User(
        businessCentralId = "",
        id = "",
        lastModifierId = "",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED,
        level = Level.MASTER,
        person = null,
        permissions = setOf(
            Permission.UPDATE_BUSINESS_CENTRAL,
            Permission.VIEW_BUSINESS_CENTRAL,
            Permission.DELETE_BUSINESS_CENTRAL,
            Permission.VIEW_LICENSING
        )
    )

    fun execute() {
        viewModelScope.launch {
            val settings = listOf(
                QuerySettings(Field.PARENT_ID, listOf(""), QueryType.WHERE_IN),
            )

            when (val response = useCase.execute(user(), settings).single()) {
                is Response.Success -> logWarn(response.toString())
                is Response.Error -> logWarn(response.toString())
                is Response.Empty -> logWarn(response.toString())
            }
        }
    }

}


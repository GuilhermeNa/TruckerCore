package com.example.truckercore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.fleet.shared.module.licensing.service.LicensingService
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingByQueryUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import java.time.LocalDateTime

internal class MyViewModel(
    private val useCase: LicensingService
) : ViewModel() {

    private fun entity() = BusinessCentral(
        businessCentralId = "",
        id = "KX1GZjhMEPAbny7Hasdvs",
        lastModifierId = "campoatualizado222",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED
    )

    private val user = User(
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
                QuerySettings(Field.PARENT_ID, QueryType.WHERE_IN, listOf(""))
            )

            val documentParam = QueryParameters.create(user)
                .setQueries(
                    QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, "parentId")
                ).build()

            when (val response = useCase.getData(documentParam).single()) {
                is Response.Success -> logWarn(response.toString())
                is Response.Error -> logWarn(response.toString())
                is Response.Empty -> logWarn(response.toString())
            }
        }
    }

}


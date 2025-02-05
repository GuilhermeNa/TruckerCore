package com.example.truckercore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.fleet.shared.module.licensing.service.LicensingService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import java.time.LocalDateTime

internal class MyViewModel(
    private val service: LicensingService
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
            Permission.VIEW_LICENSING,
            Permission.VIEW_STORAGE_FILE
        )
    )

    fun execute() {
        viewModelScope.launch {

           /* val queryParams =
                QueryParameters.create(user)
                    .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, ""))
                    .build()*/

            val documentParams = DocumentParameters.create(user)
                .setId("JXLR50V4kMRS5Qy69xQR").build()

            when (val response = service.fetchLicensingWithFiles(documentParams).single()) {
                is Response.Success -> {
                    Log.i("TAG", "execute: $response")
                }
                is Response.Error -> {
                    Log.i("TAG", "execute: $response")
                }
                is Response.Empty -> {
                    Log.i("TAG", "execute: $response")
                }
            }

        }
    }

}


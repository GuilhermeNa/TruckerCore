package com.example.truckercore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.infrastructure.security.authentication.service.AuthService
import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.modules.user.service.UserService
import com.example.truckercore.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.modules.user.use_cases.interfaces.GetUserUseCase
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.launch
import java.time.LocalDateTime

internal class MyViewModel(
    private val service: AuthService,
    private val userService: UserService
) : ViewModel() {

    private val user = User(
        businessCentralId = "123",
        id = "123456",
        lastModifierId = "123124",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED,
        isVip = false,
        vipStart = null,
        vipEnd = null,
        level = Level.MASTER,
        permissions = hashSetOf(Permission.CREATE_USER),
        personFLag = PersonCategory.ADMIN
    )

    private val userCreated = User(
        businessCentralId = "123",
        id = null,
        lastModifierId = "123124",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PENDING,
        isVip = false,
        vipStart = null,
        vipEnd = null,
        level = Level.MASTER,
        permissions = hashSetOf(Permission.VIEW_TRUCK),
        personFLag = PersonCategory.ADMIN
    )

    fun execute() {
        viewModelScope.launch {

            val requirements = NewAccessRequirements(
                uid = "123456789",
                name = "Guilherme",
                surname = "Napoleão",
                email = "napoleao.gns@hotmail.com",
                personFlag = PersonCategory.ADMIN

            )

            userService.fetchLoggedUserWithPerson("00E2QPDDE6mHvDfOI7dx", false).collect { response ->
                if (response is Response.Success)
                    logWarn(response.toString())
                else logError(response.toString())
            }

            service.createNewSystemAccess(requirements).collect { response ->
                if (response is Response.Success)
                    logWarn(response.toString())
                else logError(response.toString())
            }
        }
    }

}



package com.example.truckercore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.expressions.logWarn
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Named

internal class MyViewModel(
    private val useCase: DeleteBusinessCentralUseCase
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
        permissions = setOf(Permission.UPDATE_BUSINESS_CENTRAL, Permission.VIEW_BUSINESS_CENTRAL, Permission.DELETE_BUSINESS_CENTRAL)
    )

    fun execute() {
        viewModelScope.launch {
            useCase.execute(user(), "KX1GZjhMEPARKbny7Hvs").single()
        }
    }

}

internal class Initial(
    private val test: Test<BusinessCentralDto>
) {

    fun execute(dto: BusinessCentralDto) {
        test.execute(dto)
    }

}

interface Test<T : Dto> {
    fun execute(dto: T)
}

internal class TestImpl<T : Dto>(
    val ultimo: Ultimo<BusinessCentralDto>
) : Test<T> {
    override fun execute(dto: T) {
        logWarn(dto.toString())
        ultimo.executeUltimo()
    }
}

class Ultimo<T : Dto>() {

    fun executeUltimo() {
        logWarn("Executou o ultimo")
    }
}

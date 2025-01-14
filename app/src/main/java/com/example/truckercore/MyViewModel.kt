package com.example.truckercore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepositoryImpl
import com.example.truckercore.modules.business_central.use_cases.interfaces.CreateBusinessCentralUseCase
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.expressions.logWarn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Named

internal class MyViewModel(
    private val repository: CreateBusinessCentralUseCase
) : ViewModel() {

    private fun entity() = BusinessCentral(
        businessCentralId = "",
        id = null,
        lastModifierId = "lastModifier",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PENDING
    )

    fun execute() {
        viewModelScope.launch {
            repository.execute(entity())
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

interface Test<T: Dto> {
    fun execute(dto: T)
}

internal class TestImpl<T: Dto>(
    val ultimo: Ultimo<BusinessCentralDto>
) : Test<T> {
    override fun execute(dto: T) {
        logWarn(dto.toString())
        ultimo.executeUltimo()
    }
}

class Ultimo<T: Dto>() {

    fun executeUltimo() {
        logWarn("Executou o ultimo")
    }
}

package com.example.truckercore

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.implementations.FirebaseQueryBuilderImpl
import com.example.truckercore.infrastructure.database.firebase.implementations.NewFireBaseRepositoryImpl
import com.example.truckercore.infrastructure.database.firebase.interfaces.NewFireBaseRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepositoryImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.service.LicensingService
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class Test {

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }
    }


    val user = TestUserDataProvider.getBaseEntity()
    val service: LicensingService = mockk()

    private val firestore: FirebaseFirestore = mockk()
    private val builder = FirebaseQueryBuilderImpl(firestore)

    @Test
    fun testar() = runBlocking {
        println("Início do programa")

        coroutineScope {
            println("Início coroutine")
            launch {
                delay(1000L)  // Simula uma operação assíncrona
                println("Corrotina 1 concluída")
            }
            launch {
                delay(500L)  // Simula outra operação assíncrona
                println("Corrotina 2 concluída")
            }
        }

        println("Fim do programa")
    }


}

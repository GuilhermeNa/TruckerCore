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
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.single
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


    val testando = Testando()
    val nfb: NewFireBaseRepository = NewFireBaseRepositoryImpl(mockk(), mockk(), mockk())
    val repo = LicensingRepositoryImpl(mockk(), mockk())

    @Test
    fun testar() = runTest {
      val teste = repo.fetchById("")

}

class Testando {

    inline fun <reified T : Any> testeA(t: T): T {
        val copia = when (t) {
            is Casa -> t.copy(testado = true)
            is Apartamento -> t.copy(testado = true)
            else -> throw NullPointerException()
        }
        return copia as T
    }

}

data class Casa(
    val numero: Int,
    val rua: String,
    override val testado: Boolean = false
) : Imovel

data class Apartamento(
    val numero: Int,
    val bloco: String,
    override val testado: Boolean = false
) : Imovel

interface Imovel {
    val testado: Boolean
}
package com.example.truckercore

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.database.firebase.implementations.FirebaseQueryBuilderImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.service.LicensingService
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.modules.storage_file.dto.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
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
    private lateinit var mapper: LicensingMap

    @Test
    fun testar() {


    }

    fun testando(mapeador: FileMap) {
        val entity = Licensing
        val t = runBlock<LicensingDto>(entity)
    }

    private inline fun <reified T> runBlock(entity: Entity): T {
        return mapper.toDto(entity) as T
    }



    interface TestMap {

        fun toDto(entity: Entity): Dto

        fun toEntity(dto: Dto): Entity

    }

    class LicensingMap() : TestMap {

        override fun toDto(entity: Entity): LicensingDto {
            TODO("Not yet implemented")
        }

        override fun toEntity(dto: Dto): Licensing {
            TODO("Not yet implemented")
        }

    }

    class FileMap() : TestMap {

        override fun toDto(entity: Entity): StorageFileDto {
            TODO("Not yet implemented")
        }

        override fun toEntity(dto: Dto): StorageFile {
            TODO("Not yet implemented")
        }

    }



}

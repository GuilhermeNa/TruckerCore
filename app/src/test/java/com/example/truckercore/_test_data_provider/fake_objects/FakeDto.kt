package com.example.truckercore._test_data_provider.fake_objects

import com.example.truckercore.data.modules._shared.enums.PersistenceState
import com.example.truckercore.data.infrastructure.repository.data.contracts.Dto

data class FakeDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistenceState: PersistenceState? = null
) : Dto {

}
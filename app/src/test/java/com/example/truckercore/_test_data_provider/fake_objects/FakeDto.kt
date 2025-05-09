package com.example.truckercore._test_data_provider.fake_objects

import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.Dto

data class FakeDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistence: Persistence? = null
) : Dto {

}
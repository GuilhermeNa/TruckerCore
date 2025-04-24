package com.example.truckercore._test_data_provider

import com.example.truckercore._test_data_provider.fake_objects.FakeDto
import com.example.truckercore.model.shared.enums.Persistence

class TestDtoProvider {

    fun fakeDto(
        id: String? = "id",
        companyId: String? = "compId",
        persistence: Persistence? = Persistence.ACTIVE
    ) = FakeDto(
        id,
        companyId,
        persistence
    )


}
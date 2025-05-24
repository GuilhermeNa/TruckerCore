package com.example.truckercore._test_data_provider

import com.example.truckercore._test_data_provider.fake_objects.FakeDto
import com.example.truckercore.model.modules._shared.enums.PersistenceState

class TestDtoProvider {

    fun fakeDto(
        id: String? = "id",
        companyId: String? = "compId",
        persistence: PersistenceState? = PersistenceState.ACTIVE
    ) = FakeDto(
        id,
        companyId,
        persistence
    )


}
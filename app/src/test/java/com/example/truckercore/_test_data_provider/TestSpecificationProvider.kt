package com.example.truckercore._test_data_provider

import com.example.truckercore._test_data_provider.fake_objects.FakeID
import com.example.truckercore._test_data_provider.fake_objects.FakeSpec
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Filter
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification
import com.example.truckercore.model.modules.user.UserSpec
import com.example.truckercore.model.modules.user.data.UserID
import io.mockk.every
import io.mockk.mockk

class TestSpecificationProvider {

    fun speckMock(
        id: FakeID? = null,
    ): FakeSpec {
        val spec = mockk<FakeSpec>(relaxed = true)
        every { spec.entityId } returns id
        return spec
    }


    fun unsupportedSpecMock() = mockk<Specification<*>>(relaxed = true).apply {
        every { collectionName } returns "USER"
        every { getFilters() } returns listOf(mockk<Filter>()) // Unsupported filter
    }

    fun specMock() = mockk<Specification<*>>(relaxed = true)

    fun specIDSearch() = UserSpec(entityId = UserID("123"))

}
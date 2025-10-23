package com.example.truckercore._test_data_provider

import com.example.truckercore._test_data_provider.fake_objects.FakeID
import com.example.truckercore._test_data_provider.fake_objects.FakeSpec
import com.example.truckercore.data.infrastructure.repository.data.contracts.Filter
import com.example.truckercore.data.infrastructure.repository.data.contracts.Specification
import com.example.truckercore.data.modules.user.specification.UserSpec
import com.example.truckercore.data.modules.user.data.UserID
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
        every { collection } returns "USER"
        every { getFilter() } returns listOf(mockk<Filter>()) // Unsupported filter
    }

    fun specMock() = mockk<Specification<*>>(relaxed = true)

    fun specIDSearch() = UserSpec(entityId = UserID("123"))

}
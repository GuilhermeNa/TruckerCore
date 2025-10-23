package com.example.truckercore._test_data_provider.fake_objects

import com.example.truckercore.data.infrastructure.repository.data.contracts.Filter
import com.example.truckercore.data.infrastructure.repository.data.contracts.Specification

data class FakeSpec(
    override val entityId: FakeID? = null,
): Specification<FakeDto> {

    override val dtoClass = FakeDto::class.java
    override val collection = com.example.truckercore.core.config.collections.AppCollection.FAKE

    override fun getFilter(): List<Filter> {
        TODO("Not yet implemented")
    }

}
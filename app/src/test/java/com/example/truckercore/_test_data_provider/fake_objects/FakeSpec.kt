package com.example.truckercore._test_data_provider.fake_objects

import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Filter
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification

data class FakeSpec(
    override val entityId: FakeID? = null,
): Specification<FakeDto> {

    override val dtoClass = FakeDto::class.java
    override val collection = Collection.FAKE

    override fun getFilter(): List<Filter> {
        TODO("Not yet implemented")
    }

}
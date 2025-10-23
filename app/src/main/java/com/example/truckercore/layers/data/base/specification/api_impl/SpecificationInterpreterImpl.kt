package com.example.truckercore.layers.data.base.specification.api_impl

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.core.error.InfraException
import com.example.truckercore.layers.data.base.filter.collection.SearchFilters
import com.example.truckercore.layers.data.base.filter.filters.WhereEqual
import com.example.truckercore.layers.data.base.filter.filters.WhereIn
import com.example.truckercore.layers.data.base.specification._contracts.SpecificationInterpreter
import com.example.truckercore.layers.data.base.specification.api_impl.wrappers.DocumentWrapper
import com.example.truckercore.layers.data.base.specification.api_impl.wrappers.QueryWrapper
import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class SpecificationInterpreterImpl(private val firestore: FirebaseFirestore) :
    SpecificationInterpreter {

    override fun byId(id: ID, collection: AppCollection): DocumentWrapper {
        val document = firestore.document(id.value)
        return DocumentWrapper(document)
    }

    override fun byFilter(searchFilter: SearchFilters, collection: AppCollection): QueryWrapper {
        var baseQuery: Query = firestore.collection(collection.name)

        searchFilter.forEach { filter ->
            baseQuery = when (filter) {
                is WhereEqual -> baseQuery.whereEqualTo(filter.field.getName(), filter.value)
                is WhereIn -> baseQuery.whereIn(filter.field.name, filter.value)
                else -> throw InfraException.Specification("Unsupported filter: $filter")
            }
        }

        return QueryWrapper(baseQuery)
    }

}
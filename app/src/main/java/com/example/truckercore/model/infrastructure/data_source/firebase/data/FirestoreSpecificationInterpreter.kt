package com.example.truckercore.model.infrastructure.data_source.firebase.data

import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.infrastructure.data_source.firebase.data.api_specification.ApiDocumentReferenceSpecification
import com.example.truckercore.model.infrastructure.data_source.firebase.data.api_specification.ApiQuerySpecification
import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSourceSpecificationInterpreter
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.collections.SearchFilter
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.exceptions.SpecificationException
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.filters.WhereEqual
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.filters.WhereIn
import com.example.truckercore.model.modules._shared._contracts.entity.ID
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirestoreSpecificationInterpreter(private val firestore: FirebaseFirestore) :
    DataSourceSpecificationInterpreter {

    override fun byId(id: ID, collection: Collection): ApiDocumentReferenceSpecification {
        val document = firestore.document(id.value)
        return ApiDocumentReferenceSpecification(document)
    }

    override fun byFilter(
        searchFilter: SearchFilter,
        collection: Collection
    ): ApiQuerySpecification {
        var baseQuery: Query = firestore.collection(collection.name)

        searchFilter.forEach { filter ->
            baseQuery = when (filter) {
                is WhereEqual -> baseQuery.whereEqualTo(filter.field.getName(), filter.value)
                is WhereIn -> baseQuery.whereIn(filter.field.name, filter.value)
                else -> throw SpecificationException("Unsupported filter: $filter")
            }
        }

        return ApiQuerySpecification(baseQuery)
    }

}
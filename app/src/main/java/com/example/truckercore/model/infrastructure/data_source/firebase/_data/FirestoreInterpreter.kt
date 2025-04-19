package com.example.truckercore.model.infrastructure.data_source.firebase._data

import com.example.truckercore.model.infrastructure.integration._data.for_api.DataSourceInterpreter
import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.Specification
import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.exceptions.SpecificationException
import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.filters.WhereEqual
import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.filters.WhereIn
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirestoreInterpreter(
    private val firestore: FirebaseFirestore
) : DataSourceInterpreter<DocumentReference, Query> {

    override fun interpretIdSearch(spec: Specification<*>): DocumentReference =
        firestore.collection(spec.collectionName).document(spec.id)

    override fun interpretFilterSearch(spec: Specification<*>): Query {
        var baseQuery: Query = firestore.collection(spec.collectionName)

        spec.getFilters().forEach { f ->
            baseQuery = when(f){
                is WhereEqual -> baseQuery.whereEqualTo(f.field.getName(), f.value)
                is WhereIn -> TODO("Not used yet")
                else -> throw SpecificationException("Unsupported filter: $f")
            }
        }

        return baseQuery
    }

}
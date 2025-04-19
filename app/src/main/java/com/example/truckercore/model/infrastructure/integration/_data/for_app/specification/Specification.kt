package com.example.truckercore.model.infrastructure.integration._data.for_app.specification

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query

interface Specification<T: BaseDto> {

    val dtoClass: Class<T>

    val collection: Collection
    val collectionName get() = collection.name

    fun byId(baseRef: CollectionReference): DocumentReference

    fun byFilters(baseQuery: Query): Query

}
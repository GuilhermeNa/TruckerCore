package com.example.truckercore.layers.data.base.specification.api_impl.wrappers

import com.example.truckercore.layers.data.base.specification._contracts.ApiSpecificationWrapper
import com.example.truckercore.layers.data.base.specification._contracts.Specification
import com.example.truckercore.layers.data.data_source.data.DataSource
import com.google.firebase.firestore.DocumentReference

/**
 * Wrapper around a Firestore [DocumentReference] to be used as a [Specification]
 * in the [DataSource] layer.
 *
 * Implements [ApiSpecificationWrapper] to allow generic handling of backend specifications.
 *
 * @property document The Firestore [DocumentReference] representing a single document.
 */
class DocumentWrapper(val document: DocumentReference) : ApiSpecificationWrapper
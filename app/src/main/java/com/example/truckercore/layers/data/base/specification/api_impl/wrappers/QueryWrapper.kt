package com.example.truckercore.layers.data.base.specification.api_impl.wrappers

import com.example.truckercore.layers.data.base.specification._contracts.ApiSpecificationWrapper
import com.example.truckercore.layers.data.base.specification._contracts.Specification
import com.example.truckercore.layers.data.data_source.data.DataSource
import com.google.firebase.firestore.Query

/**
 * Wrapper around a Firestore [Query] to be used as a [Specification] in the [DataSource] layer.
 *
 * Implements [ApiSpecificationWrapper] to allow generic handling of backend specifications.
 *
 * @property query The Firestore [Query] representing a query that can return multiple documents.
 */
class QueryWrapper(val query: Query) : ApiSpecificationWrapper
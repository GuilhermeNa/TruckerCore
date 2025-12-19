package com.example.truckercore.layers.data.base.specification._contracts

import com.example.truckercore.layers.data.data_source.data.DataSource

/**
 * Marker interface representing a backend-specific wrapper for an API query or document.
 *
 * Implementations of this interface encapsulate the details required to execute a
 * query or fetch a document from a specific backend (e.g., Firebase Firestore).
 *
 * This allows the [DataSource] layer to operate in a generic way using [Specification]s
 * without being tightly coupled to the underlying data source implementation.
 */
interface ApiSpecificationWrapper
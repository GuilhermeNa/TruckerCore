package com.example.truckercore.layers.data.base.outcome

/**
 * Marker interface for modeling outcomes of operations that can return data or just signal success/failure.
 *
 * Typical usage is to abstract over operation results in domain, use cases, repositories, and services,
 * while preserving more specific semantics (e.g., differentiating empty results from successful ones).
 *
 * @see DataOutcome
 * @see OperationOutcome
 */
interface Outcome<out T>
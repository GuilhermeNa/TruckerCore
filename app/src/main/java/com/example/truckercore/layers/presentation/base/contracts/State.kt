package com.example.truckercore.layers.presentation.base.contracts

/**
 * Marker interface representing a persistent state in an MVI architecture.
 *
 * States model the current snapshot of the UI, encapsulating all data necessary
 * for rendering the screen. Unlike [Effect], states are stored, observed, and
 * can be updated over time in response to [Event]s.
 *
 * Implementations of this interface should be immutable and fully represent
 * the UI at a given point in time.
 */
interface State
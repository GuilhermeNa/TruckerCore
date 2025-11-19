package com.example.truckercore.layers.presentation.base.contracts

import com.example.truckercore.layers.presentation.base.reducer.Reducer

/**
 * Marker interface representing a user or system action in an MVI architecture.
 *
 * Events describe interactions or occurrences that may trigger state updates or
 * side effects. Examples include:
 * - Button clicks
 * - Text input changes
 * - Completion or failure of asynchronous tasks
 *
 * Events are typically dispatched to a [Reducer] to produce a new [State] and/or
 * [Effect].
 */
interface Event
package com.example.truckercore.layers.presentation.base.contracts

import com.example.truckercore.layers.presentation.base.reducer.Reducer

/**
 * Marker interface representing a one-time side effect in an MVI architecture.
 *
 * Effects are used to communicate transient events from the [Reducer] to the UI or
 * other observers, such as:
 * - Navigation actions
 * - Toast or Snackbar messages
 * - Dialog triggers
 *
 * Effects are not persisted in the state and are intended to be consumed only once.
 */
interface Effect
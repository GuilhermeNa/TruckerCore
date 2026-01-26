package com.example.truckercore.layers.presentation.base.abstractions.view.public

import com.example.truckercore.layers.presentation.base.abstractions.view.base.LockedFragment

/**
 * Base fragment for public screens that require exit confirmation
 * but do not require user authentication.
 */
abstract class PublicLockedFragment : LockedFragment()
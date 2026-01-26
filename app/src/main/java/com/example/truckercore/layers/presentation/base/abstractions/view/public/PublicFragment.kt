package com.example.truckercore.layers.presentation.base.abstractions.view.public

import com.example.truckercore.layers.presentation.base.abstractions.view.base.BaseFragment

/**
 * Base fragment for screens that do not require authentication.
 *
 * A `PublicFragment` represents content that is accessible to all users,
 * regardless of authentication state (e.g., login, onboarding, or
 * informational screens).
 *
 * No user validation is performed by default.
 */
abstract class PublicFragment : BaseFragment()
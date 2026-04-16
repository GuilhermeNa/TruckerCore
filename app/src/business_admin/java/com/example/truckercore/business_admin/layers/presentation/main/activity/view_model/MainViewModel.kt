package com.example.truckercore.business_admin.layers.presentation.main.activity.view_model

import com.example.truckercore.business_admin.layers.presentation.main.activity.ActivityMenu
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.core.my_lib.expressions.get
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.singletons.session.SessionManager
import com.example.truckercore.layers.domain.use_case.authentication.GetUserEmailUseCase
import com.example.truckercore.layers.domain.use_case.authentication.GetUserNameUseCase
import com.example.truckercore.layers.domain.use_case.authentication.SignOutUseCase
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * MainViewModel
 *
 * Responsible for managing user session data and UI-related state
 * for MainActivity.
 *
 * Responsibilities:
 * - Provides authenticated user information (name and email).
 * - Handles logout action.
 * - Controls Toolbar menu visibility state.
 *
 * Architecture:
 * - Follows MVVM principles.
 * - Delegates business logic to UseCases.
 * - Exposes immutable UI state via StateFlow.
 * - Extends BaseViewModel for shared ViewModel behavior.
 */
class MainViewModel(
    getUserNameUseCase: GetUserNameUseCase,
    getUserEmailUseCase: GetUserEmailUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val sessionManager: SessionManager
) : BaseViewModel() {

    val sessionFlow get() = sessionManager.sessionFLow

    // Authenticated user's display name.
    val name: Name = getUserNameUseCase().get()

    // Authenticated user's email address.
    val email: Email = getUserEmailUseCase().get()

    /**
     * Backing property for Toolbar menu visibility state.
     *
     * Default value: true (menu enabled).
     */
    private val _menuState = MutableStateFlow(ActivityMenu.DEFAULT)
    val menuState get() = _menuState.asStateFlow()

    //----------------------------------------------------------------------------------------------
    fun companyId(): CompanyID {
        TODO()/*sessionFLow.value*/
    }

    /**
     * Executes the logout process.
     *
     * Responsibilities:
     * - Delegates sign-out logic to the domain layer.
     * - Keeps ViewModel free of authentication implementation details.
     */
    fun logout() {
        signOutUseCase()
    }

    /**
     * Disables the Toolbar menu.
     *
     * Typically used when navigating to destinations
     * triggered from the options menu to prevent
     * duplicate navigation events.
     */
    fun disableMenu() {
        _menuState.value = ActivityMenu.NONE
    }

    /**
     * Enables the Toolbar menu.
     *
     * Ensures state change occurs only if necessary
     * to avoid redundant emissions.
     */
    fun enableDefaultMenu() {
        _menuState.value = ActivityMenu.DEFAULT
    }

}
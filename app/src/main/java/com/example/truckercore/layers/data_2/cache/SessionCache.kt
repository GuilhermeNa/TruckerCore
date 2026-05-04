package com.example.truckercore.layers.data_2.cache

import com.example.truckercore.core.error.default_errors.CacheException
import com.example.truckercore.layers.domain.base.ids.AccessID
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.access.Role

object SessionCache {

    private var _companyID: CompanyID? = null
    val companyID get() = _companyID ?: throw CacheException(
        "CompanyID has not been initialized in SessionCache. " +
                "Make sure to call SessionCache.initialize() after user login."
    )

    private var _userID: UserID? = null
    val userID get() = _userID ?: throw CacheException(
        "UserID is missing in SessionCache. " +
                "The session cache was not properly initialized."
    )

    private var _accessID: AccessID? = null
    val accessID get() = _accessID ?: throw CacheException(
        "AccessID is not available in SessionCache. " +
                "Check the authentication flow and session initialization."
    )

    private var _role: Role? = null
    val role get() = _role ?: throw CacheException(
        "Role is not defined in SessionCache. " +
                "This indicates an incomplete session state."
    )

    //----------------------------------------------------------------------------------------------
    private val isInitialized
        get() =
            _companyID != null && _userID != null &&
                    _accessID != null && _role != null

    fun initialize(
        companyID: CompanyID, userID: UserID,
        accessID: AccessID, role: Role
    ) {
        if (isInitialized) return

        _companyID = companyID
        _userID = userID
        _accessID = accessID
        _role = role
    }

}
package com.example.truckercore.layers.domain.singletons.session

import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.layers.domain.model.session.Session
import com.example.truckercore.layers.presentation.base.contracts.State

sealed class SessionState : State {

    data object Loading : SessionState()

    data class Success(val session: Session) : SessionState()

    data class Error(val exception: AppException) : SessionState()


    //----------------------------------------------------------------------------------------------

    val companyId get() = (this as? Success)?.session?.companyId()

    val SessionState.isLoading get() = this is Loading
    val SessionState.isSuccess get() = this is Success
    val SessionState.isError get() = this is Error


    fun isActive(): Boolean? = (this as? Success)?.session?.active

    inline fun exitApp(onError: () -> Unit, onLogout: () -> Unit) {
        if(isLoading) return
        when {
            isError -> onError()
            isActive() == null -> onError()
            !isActive()!! -> onLogout()
            else -> Unit
        }
    }

}
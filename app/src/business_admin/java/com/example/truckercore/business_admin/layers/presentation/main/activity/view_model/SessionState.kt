package com.example.truckercore.business_admin.layers.presentation.main.activity.view_model

import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.layers.domain.model.session.Session

sealed class SessionState {

    data object Loading: SessionState()

    data class Success(val value: Session): SessionState()

    data class Error(val exception: AppException): SessionState()



}
package com.example.truckercore.layers.presentation.viewmodels.view_models.user_name.use_case

import com.example.truckercore.core.classes.FullName
import com.example.truckercore.core.expressions.extractData
import com.example.truckercore.core.expressions.mapAppResult
import com.example.truckercore.data.modules.aggregation.system_access.factory.SystemAccessForm
import com.example.truckercore.data.modules.aggregation.system_access.manager.SystemAccessManager
import com.example.truckercore.data.modules.authentication.manager.AuthManager
import com.example.truckercore.domain._shared.helpers.ViewError
import com.example.truckercore.domain._shared.helpers.ViewResult

class UserNameViewUseCase(
    private val authManager: AuthManager,
    private val accessManager: SystemAccessManager,
    private val flavorService: com.example.truckercore.core.config.flavor.FlavorService
) {

    suspend operator fun invoke(name: FullName): ViewResult<Unit> {
        val email = authManager.getUserEmail().extractData()
        val uid = authManager.getUID().extractData()
        val role = flavorService.getRole()

        val form = SystemAccessForm(uid, role, name, email)

        return accessManager.createSystemAccess(form).mapAppResult(
            onSuccess = { ViewResult.Success(Unit) },
            onError = { handleError(it) }
        )
    }

    private fun handleError(e: com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException): ViewResult<Unit> {
        val viewError =
            if (e is com.example.truckercore.core.error.classes.data.InfraException.NetworkUnavailable) {
                ViewError.Recoverable("Falha na conexão, tente novamente.")
            } else ViewError.Critical

        return ViewResult.Error(viewError)
    }

}
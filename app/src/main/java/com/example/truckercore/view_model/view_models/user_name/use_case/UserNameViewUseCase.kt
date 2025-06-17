package com.example.truckercore.view_model.view_models.user_name.use_case

import com.example.truckercore._shared.classes.FullName
import com.example.truckercore._shared.expressions.extractData
import com.example.truckercore._shared.expressions.mapAppResult
import com.example.truckercore.model.configs.flavor.FlavorService
import com.example.truckercore.model.errors.AppException
import com.example.truckercore.model.errors.infra.InfraException
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.model.modules.aggregation.system_access.factory.SystemAccessForm
import com.example.truckercore.model.modules.aggregation.system_access.manager.SystemAccessManager
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model._shared.helpers.ViewError
import com.example.truckercore.view_model._shared.helpers.ViewResult

class UserNameViewUseCase(
    private val authManager: AuthManager,
    private val accessManager: SystemAccessManager,
    private val flavorService: FlavorService
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

    private fun handleError(e: AppException): ViewResult<Unit> {
        val viewError =
            if (e is InfraException.NetworkUnavailable) {
                ViewError.Recoverable("Falha na conexão, tente novamente.")
            } else ViewError.Critical

        return ViewResult.Error(viewError)
    }

}
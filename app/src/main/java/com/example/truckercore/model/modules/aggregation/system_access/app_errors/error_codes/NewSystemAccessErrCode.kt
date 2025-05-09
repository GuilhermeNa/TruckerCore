package com.example.truckercore.model.modules.aggregation.system_access.app_errors.error_codes

import com.example.truckercore.model.modules.aggregation.system_access.app_errors.SystemAccessErrCode

sealed class NewSystemAccessErrCode : SystemAccessErrCode {

    /**
     * Error code indicating a failure while attempting to create or initialize a system access object.
     * This may occur due to misconfigurations, missing dependencies, or other internal issues in the factory logic.
     */
    data object Factory : NewSystemAccessErrCode() {
        override val name: String = "FACTORY_FAILED"
        override val userMessage: String = "Não foi possível inicializar corretamente o acesso ao sistema."
        override val logMessage: String = "Failed to create an instance of the system access object. Check factory configuration and internal dependencies."
        override val isRecoverable: Boolean = false
    }

}
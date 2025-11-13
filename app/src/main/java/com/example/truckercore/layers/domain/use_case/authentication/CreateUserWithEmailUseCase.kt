package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.core.my_lib.classes.EmailCredential
import com.example.truckercore.layers.data.base.outcome.OperationOutcome

interface CreateUserWithEmailUseCase {

    operator fun invoke(credential: EmailCredential): OperationOutcome

}
package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.shared.modules.personal_data.repositories.interfaces.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.ValidatePersonalDataUseCase

internal class ValidatePersonalDataUseCaseImpl(
    override val repository: PersonalDataRepository
) : ValidatePersonalDataUseCase {

    override fun execute() {

    }

}
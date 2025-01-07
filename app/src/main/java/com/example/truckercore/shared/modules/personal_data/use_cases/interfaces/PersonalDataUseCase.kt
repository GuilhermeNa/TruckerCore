package com.example.truckercore.shared.modules.personal_data.use_cases.interfaces

import com.example.truckercore.shared.modules.personal_data.repositories.interfaces.PersonalDataRepository

internal interface PersonalDataUseCase {

    val repository: PersonalDataRepository

}
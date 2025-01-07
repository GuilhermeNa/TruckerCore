package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.modules.personal_data.repositories.interfaces.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.UpdatePersonalDataStatusUseCase

internal class UpdatePersonalDataStatusUseCaseImpl(
    override val repository: PersonalDataRepository
) : UpdatePersonalDataStatusUseCase {
    override fun execute(user: User, id: String, newState: PersistenceStatus) {
        TODO("Not yet implemented")
    }


}
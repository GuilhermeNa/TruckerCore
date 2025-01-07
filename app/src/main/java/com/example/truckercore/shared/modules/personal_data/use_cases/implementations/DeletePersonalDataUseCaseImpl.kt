package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.personal_data.repositories.interfaces.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.DeletePersonalDataUseCase

internal class DeletePersonalDataUseCaseImpl(
    private val repository: PersonalDataRepository
) : DeletePersonalDataUseCase {
    override fun execute(user: User, id: String) {
        TODO("Not yet implemented")
    }


}
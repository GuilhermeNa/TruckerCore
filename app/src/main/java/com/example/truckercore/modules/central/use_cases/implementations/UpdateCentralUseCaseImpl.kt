package com.example.truckercore.modules.central.use_cases.implementations

import com.example.truckercore.modules.central.repository.CentralRepository
import com.example.truckercore.modules.central.use_cases.interfaces.CentralUseCase
import com.example.truckercore.modules.central.use_cases.interfaces.UpdateCentralUseCase

internal class UpdateCentralUseCaseImpl(override val repository: CentralRepository) :
    UpdateCentralUseCase {

    override fun execute(dto: CentralUseCase) {

    }

}
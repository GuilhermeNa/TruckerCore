package com.example.truckercore.modules.central.use_cases.interfaces

import com.example.truckercore.modules.central.repository.CentralRepository

internal interface CentralUseCase {

    val repository: CentralRepository

}
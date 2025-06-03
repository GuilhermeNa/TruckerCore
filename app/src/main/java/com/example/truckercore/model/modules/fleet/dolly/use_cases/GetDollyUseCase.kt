package com.example.truckercore.model.modules.fleet.dolly.use_cases

import com.example.truckercore._shared.classes.AppResponse
import com.example.truckercore.model.modules.fleet.dolly.data.Dolly
import com.example.truckercore.model.modules.fleet.dolly.specification.DollySpec

interface GetDollyUseCase {

    suspend operator fun invoke(spec: DollySpec): AppResponse<Dolly>

}
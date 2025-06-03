package com.example.truckercore.model.modules.fleet.grain_trailer.use_cases

import com.example.truckercore._shared.classes.AppResponse
import com.example.truckercore.model.modules.fleet.grain_trailer.data.GrainTrailer
import com.example.truckercore.model.modules.fleet.grain_trailer.specification.GrainTrailerSpec

interface GetGrainTrailerUseCase {

    suspend operator fun invoke(spec: GrainTrailerSpec): AppResponse<GrainTrailer>

}
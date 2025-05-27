package com.example.truckercore.model.modules.fleet.dry_van.use_cases

import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore.model.modules.fleet.dry_van.data.DryVan
import com.example.truckercore.model.modules.fleet.dry_van.specification.DryVanSpec

interface GetDryVanUseCase {

    suspend operator fun invoke(spec: DryVanSpec): AppResponse<DryVan>

}
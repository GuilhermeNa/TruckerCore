package com.example.truckercore.model.modules.fleet.dump.use_cases

import com.example.truckercore._shared.classes.AppResponse
import com.example.truckercore.model.modules.fleet.dump.data.Dump
import com.example.truckercore.model.modules.fleet.dump.specification.DumpSpec

interface GetDumpUseCase {

    suspend operator fun invoke(spec: DumpSpec): AppResponse<Dump>

}
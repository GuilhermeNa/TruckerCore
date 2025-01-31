package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces

import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

interface GetLicensingByParentIdsUseCase {

    suspend fun execute(user: User, vararg parentId: String): Flow<Response<List<Licensing>>>

}
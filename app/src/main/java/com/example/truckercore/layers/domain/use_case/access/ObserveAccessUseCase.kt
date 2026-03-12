package com.example.truckercore.layers.domain.use_case.access

import com.example.truckercore.infra.logger.Logable
import com.example.truckercore.layers.data.base.dto.impl.AccessDto
import com.example.truckercore.layers.data.base.dto.impl.UserDto
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.specification.specs_impl.AccessSpec
import com.example.truckercore.layers.data.base.specification.specs_impl.UserSpec
import com.example.truckercore.layers.data.repository.data.DataRepository
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.access.Access
import com.example.truckercore.layers.domain.model.user.UserDraft
import kotlinx.coroutines.flow.Flow

class ObserveAccessUseCase(
    private val repository: DataRepository
): Logable {

    operator fun invoke(id: UserID): Flow<DataOutcome<Access>> {

    }

    private fun flow(id: UserID) =
        repository.flowByFilter<AccessDto, Access>(AccessSpec(userId = id))

}
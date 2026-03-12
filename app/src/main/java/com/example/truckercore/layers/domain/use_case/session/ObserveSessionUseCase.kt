package com.example.truckercore.layers.domain.use_case.session

import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.my_lib.expressions.get
import com.example.truckercore.infra.logger.Logable
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.specification.specs_impl.UserSpec
import com.example.truckercore.layers.data.repository.data.DataRepository
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.model.session.Session
import com.example.truckercore.layers.domain.model.user.UserDraft
import com.example.truckercore.layers.domain.use_case.authentication.GetUidUseCase
import com.example.truckercore.layers.domain.use_case.user.ObserveUserDraftUseCase
import kotlinx.coroutines.flow.Flow

class ObserveSessionUseCase(
    private val getUidUseCase: GetUidUseCase,
    private val observeUserUseCase: ObserveUserDraftUseCase
) : Logable {

    operator fun invoke(): Flow<DataOutcome<Session>> {
        try {
            val uid = getUidUseCase().get()
            val flow = observeUserUseCase(uid)


        } catch (e: AppException) {
            DataOutcome.Failure(e)
        } catch (e: Exception) {
            DataOutcome.Failure(e)
        }

    }

    private fun DataOutcome<List<UserDraft>>.handle() {

    }


}
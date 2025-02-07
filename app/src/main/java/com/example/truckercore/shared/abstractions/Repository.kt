package com.example.truckercore.shared.abstractions

import com.example.truckercore.infrastructure.database.firebase.util.FirebaseRequest
import com.example.truckercore.shared.utils.parameters.SearchParameters

abstract class Repository {

    protected abstract fun createFirestoreRequest(params: SearchParameters): FirebaseRequest<*>

}
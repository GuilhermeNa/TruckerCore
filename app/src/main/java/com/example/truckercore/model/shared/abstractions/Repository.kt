package com.example.truckercore.model.shared.abstractions

import com.example.truckercore.model.infrastructure.database.firebase.util.FirebaseRequest
import com.example.truckercore.model.shared.utils.parameters.SearchParameters

/**
 * Represents an abstract repository for interacting with Firestore. This class serves as a base class
 * that will be implemented by specific repositories for various entities within the system.
 *
 * The `createFirestoreRequest` function, defined as abstract, is meant to be implemented by each specific
 * repository for the entity it manages. These repositories will handle interactions with the external Firestore
 * repository and customize the queries to meet the entity's requirements.
 */
internal abstract class Repository {

    protected abstract fun createFirestoreRequest(params: SearchParameters): FirebaseRequest<*>

}
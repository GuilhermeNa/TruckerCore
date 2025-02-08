package com.example.truckercore.infrastructure.database.firebase.util

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.errors.FirebaseRequestException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.SearchParameters

class FirebaseRequest<T : Dto> private constructor(
    val collection: Collection,
    val clazz: Class<T>,
    val params: SearchParameters
) {

    fun getDocumentParams(): DocumentParameters =
        if (params is DocumentParameters) params
        else throw FirebaseRequestException(
            "Expected a DocumentParameters but received a QueryParameters."
        )

    fun getQueryParams(): QueryParameters =
        if (params is QueryParameters) params
        else throw FirebaseRequestException(
            "Expected a QueryParameters but received a DocumentParameters."
        )

    fun shouldObserve() = params.shouldStream

    companion object {
        fun <T : Dto> create(clazz: Class<T>) = Builder(clazz)
    }

    class Builder<T : Dto>(private val clazz: Class<T>) {

        private var params: SearchParameters? = null
        private var collection: Collection? = null

        fun setParams(newParams: SearchParameters) = apply { this.params = newParams }

        fun setCollection(newCollection: Collection) = apply { this.collection = newCollection }

        fun build(): FirebaseRequest<T> {
            val validCollection = checkCollection()
            val validParams = checkParams()

            return FirebaseRequest(
                collection = validCollection,
                clazz = clazz,
                params = validParams
            )
        }

        private fun checkParams(): SearchParameters =
            params ?: throw IllegalArgumentException("You must provide a Search Parameter.")

        private fun checkCollection(): Collection =
            collection ?: throw IllegalArgumentException("You must provide a Collection reference.")

    }

}

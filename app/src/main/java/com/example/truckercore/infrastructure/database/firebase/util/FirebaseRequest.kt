package com.example.truckercore.infrastructure.database.firebase.util

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.parameters.SearchParameters

class FirebaseRequest<T : Dto> private constructor(
    val collection: Collection,
    val clazz: Class<T>,
    val params: SearchParameters
) {

    fun shouldObserve() = params.liveObserver

    companion object {
        fun <T : Dto> create(clazz: Class<T>) = Builder(clazz)
    }

    class Builder<T : Dto>(private val clazz: Class<T>) {

        private var params: SearchParameters? = null
        private var collection: Collection? = null

        fun setParams(newParams: SearchParameters): Builder<T> {
            this.params = newParams
            return this
        }

        fun setCollection(newCollection: Collection): Builder<T> {
            this.collection = newCollection
            return this
        }

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

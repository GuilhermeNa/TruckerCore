package com.example.truckercore.shared.utils.parameters

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.expressions.logWarn

class DocumentParameters private constructor(
    val user: User,
    val id: String,
    val liveObserver: Boolean
) {

    companion object {
        fun create(user: User) = Builder(user)
        internal const val ERROR_MESSAGE =
            "You must provide the ID before build a DocumentParameter."
    }

    class Builder(val user: User) {

        private var id: String = ""
        private var liveObserver: Boolean = false

        fun setId(newId: String) = apply { this.id = newId }

        fun setLiveObserver(newLiveObserver: Boolean) =
            apply { this.liveObserver = newLiveObserver }

        fun build(): DocumentParameters {
            if (id.isBlank()) handleError()
            return DocumentParameters(user, this.id, liveObserver)
        }

        private fun handleError(): Nothing {
            logWarn(javaClass, ERROR_MESSAGE)
            throw IllegalArgumentException(ERROR_MESSAGE)
        }

    }

}
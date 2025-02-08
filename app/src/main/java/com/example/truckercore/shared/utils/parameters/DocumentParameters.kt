package com.example.truckercore.shared.utils.parameters

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.expressions.logWarn

class DocumentParameters private constructor(
    override val user: User,
    val id: String,
    override val shouldStream: Boolean
) : SearchParameters {

    init {
        if (id.isBlank()) {
            logWarn(javaClass, BLANK_ID_ERROR_MESSAGE)
            throw IllegalArgumentException(BLANK_ID_ERROR_MESSAGE)
        }
    }

    companion object {
        fun create(user: User) = Builder(user)
        internal const val BLANK_ID_ERROR_MESSAGE =
            "You must provide the ID before build a DocumentParameter."
    }

    class Builder(val user: User) {
        private var id: String = ""
        private var shouldStream: Boolean = false

        fun setId(newId: String) = apply { this.id = newId }

        fun setStream(shouldStream: Boolean) = apply { this.shouldStream = shouldStream }

        fun build(): DocumentParameters = DocumentParameters(user, this.id, shouldStream)

    }

}


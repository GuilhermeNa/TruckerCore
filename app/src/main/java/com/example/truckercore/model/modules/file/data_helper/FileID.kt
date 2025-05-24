package com.example.truckercore.model.modules.file.data_helper

import com.example.truckercore.model.modules.file.exceptions.InvalidFileIdException
import com.example.truckercore.model.modules._shared.contracts.entity.ID

@JvmInline
value class FileID(override val value: String) : ID {

    init {
        if (value.isBlank()) {
            throw InvalidFileIdException("Invalid FileID: ID must be a non-blank string.")
        }
    }

}
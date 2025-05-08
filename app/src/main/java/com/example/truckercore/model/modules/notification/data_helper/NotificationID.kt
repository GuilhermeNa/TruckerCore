package com.example.truckercore.model.modules.notification.data_helper

import com.example.truckercore.model.modules.notification.exceptions.InvalidNotificationIdException
import com.example.truckercore.model.modules._contracts.ID

@JvmInline
value class NotificationID(override val value: String): ID {

    init {
        if (value.isBlank()) {
            throw InvalidNotificationIdException("Invalid NotificationId: ID must be a non-blank string.")
        }
    }

}
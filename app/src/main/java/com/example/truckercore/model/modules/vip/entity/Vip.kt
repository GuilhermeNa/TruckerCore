package com.example.truckercore.model.modules.vip.entity

import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.errors.InvalidStateException
import com.example.truckercore.model.shared.interfaces.Entity
import java.time.LocalDateTime

/**
 * Represents a VIP entity with associated details such as VIP start date, VIP end date,
 * notification date, and the status of the VIP.
 * Inherits common properties from the `Entity` interface.
 *
 * @param businessCentralId The identifier for the business central associated with this VIP entity.
 * @param id The unique identifier for this VIP entity (can be null).
 * @param lastModifierId The identifier of the user who last modified this VIP entity.
 * @param creationDate The timestamp when this VIP entity was created.
 * @param lastUpdate The timestamp when this VIP entity was last updated.
 * @param persistenceStatus The current persistence status of the VIP entity.
 * @param vipStart The timestamp when the VIP status starts.
 * @param vipEnd The timestamp when the VIP status ends.
 * @param notificationDate The timestamp when the VIP status notification should be sent.
 * @param userId The identifier of the user associated with this VIP entity.
 * @param isActive A boolean value indicating if the VIP status is currently active.
 *
 * @throws InvalidStateException If the `vipStart` date is after the `vipEnd` date.
 * @throws InvalidStateException If the `notificationDate` is after the `vipEnd` date.
 */
data class Vip(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    val vipStart: LocalDateTime,
    val vipEnd: LocalDateTime,
    val notificationDate: LocalDateTime,
    val userId: String,
    val isActive: Boolean
) : Entity {

    init {

        if (vipStart.isAfter(vipEnd))
            throw InvalidStateException("VIP start date cannot be after the VIP end date.")

        if (notificationDate.isAfter(vipEnd))
            throw InvalidStateException("Notification date cannot be after the VIP end date.")

        if (vipStart.isAfter(notificationDate))
            throw InvalidStateException("Vip start date cannot be after the notification date.")

    }

}
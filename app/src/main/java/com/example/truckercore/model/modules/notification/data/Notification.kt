package com.example.truckercore.model.modules.notification.data

import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.notification.data_helper.NotificationID
import com.example.truckercore.model.modules.notification.data_helper.Recipient
import com.example.truckercore.model.modules.notification.data_helper.RelatedEntity
import com.example.truckercore.model.modules._shared.enums.PersistenceState
import com.example.truckercore.model.modules._shared._contracts.entity.Entity

data class Notification(
    override val id: NotificationID,
    override val companyId: CompanyID,
    override val persistenceState: PersistenceState,
    val relatedEntity: RelatedEntity?,
    val recipient: Recipient,
    val title: String,
    val message: String,
    val isReadBy: List<String> = emptyList(), //
) : Entity<Notification> {

    override fun copyWith(persistence: PersistenceState): Notification {
       return copy(persistenceState = persistence)
    }

}

package com.example.truckercore.model.modules.notification.data

import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.notification.data_helper.NotificationID
import com.example.truckercore.model.modules.notification.data_helper.Recipient
import com.example.truckercore.model.modules.notification.data_helper.RelatedEntity
import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.modules._contracts.Entity

data class Notification(
    override val id: NotificationID,
    override val companyId: CompanyID,
    override val persistence: Persistence,
    val relatedEntity: RelatedEntity?,
    val recipient: Recipient,
    val title: String,
    val message: String,
    val isReadBy: List<String> = emptyList(), //
) : Entity {



}

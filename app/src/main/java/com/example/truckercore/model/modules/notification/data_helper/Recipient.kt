package com.example.truckercore.model.modules.notification.data_helper

sealed class Recipient {
    data class Single(val userId: String) : Recipient()
    data class Group(val groupId: List<String>) : Recipient()
}
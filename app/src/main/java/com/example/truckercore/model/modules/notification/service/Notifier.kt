package com.example.truckercore.model.modules.notification.service

interface Notifier<T> {

    fun notify(event: T)

}
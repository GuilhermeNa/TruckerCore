package com.example.truckercore.model.configs.di

import com.example.truckercore.model.infrastructure.utils.task_manager.TaskManagerImpl
import com.example.truckercore.model.infrastructure.utils.task_manager.TaskManager
import com.google.firebase.auth.FirebaseUser
import org.koin.dsl.module

val utilModule = module {
    factory<TaskManager<Unit>> { TaskManagerImpl() }
    factory<TaskManager<FirebaseUser>> { TaskManagerImpl() }
}
package com.example.truckercore.model.configs.di

import com.example.truckercore.model.z_to_delete.task_manager.TaskManager
import com.example.truckercore.model.z_to_delete.task_manager.TaskManagerImpl
import com.google.firebase.auth.FirebaseUser
import org.koin.dsl.module

val utilModule = module {
    factory<TaskManager<Unit>> { TaskManagerImpl() }
    factory<TaskManager<FirebaseUser>> { TaskManagerImpl() }
}
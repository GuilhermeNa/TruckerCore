package com.example.truckercore.model.modules._shared.contracts.entity

import com.example.truckercore.model.configs.annotations.InternalUseOnly
import com.example.truckercore.model.modules._shared.enums.PersistenceState

interface Persistable<T> {

    val persistenceState: PersistenceState

    @InternalUseOnly
    fun copyWith(persistence: PersistenceState): T

    fun activePersistence() = copyWith(persistence = PersistenceState.ACTIVE)

    fun archivePersistence() = copyWith(persistence = PersistenceState.ARCHIVED)

    fun deletePersistence() = copyWith(persistence = PersistenceState.DELETED)

}
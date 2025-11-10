package com.example.truckercore.infra.security

enum class Action {
    CREATE, READ, UPDATE, DELETE, APPROVE;

    fun isCreate() = this == CREATE

    fun isRead() = this == READ

    fun isUpdate() = this == UPDATE

    fun isDelete() = this == DELETE

    fun isApprove() = this == APPROVE

}
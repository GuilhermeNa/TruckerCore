package com.example.truckercore.core.error.core

interface ErrorMapper {

    operator fun invoke(e: Throwable): AppException

}
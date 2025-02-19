package com.example.truckercore.infrastructure.security.authentication.expressions

import java.security.MessageDigest

fun String.toHash(algorithm: String = "SHA-256"): String {
    return MessageDigest
        .getInstance(algorithm)
        .digest(this.toByteArray())
        .fold("") { str, byte ->
            str + "%02x".format(byte)
        }
}
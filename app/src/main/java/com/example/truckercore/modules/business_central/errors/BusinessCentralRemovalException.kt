package com.example.truckercore.modules.business_central.errors

import com.example.truckercore.shared.errors.RemovalException

class BusinessCentralRemovalException(message: String? = null, cause: Exception? = null) :
    RemovalException(message, cause)
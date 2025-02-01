package com.example.truckercore.modules.fleet.shared.module.licensing.configs

import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile

data class LicensingWithFile(
    val licensing: Licensing,
    val file: StorageFile
)
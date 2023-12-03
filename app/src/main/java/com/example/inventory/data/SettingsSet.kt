package com.example.inventory.data

data class SettingsSet(
    val shouldUseDefaults: Boolean? = null,
    val shouldHideSensitiveData: Boolean? = null,
    val shouldUseDataSharing: Boolean? = null,
    val defaultSupplierName: String? = null,
    val defaultSupplierPhone: String? = null,
    val defaultSupplierEmail: String? = null
) {
}
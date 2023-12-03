package com.example.inventory.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class SharedPreferencesSettingsRepository(context: Context) : SettingsRepository {
    private val masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val securePreferences = EncryptedSharedPreferences.create(
        PREF_FILENAME,
        masterKeys,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun getSettings() : SettingsSet {
        val x = SettingsSet(
            shouldHideSensitiveData = securePreferences.getBoolean(PREF_SHOULD_HIDE_SENSITIVE_DATA, false),
            shouldUseDefaults = securePreferences.getBoolean(PREF_SHOULD_USE_DEFAULTS, false),
            shouldUseDataSharing = securePreferences.getBoolean(PREF_SHOULD_USE_DATA_SHARING, false),
            defaultSupplierName = securePreferences.getString(PREF_DEFAULT_SUPPLIER_NAME, ""),
            defaultSupplierPhone = securePreferences.getString(PREF_DEFAULT_SUPPLIER_PHONE, ""),
            defaultSupplierEmail = securePreferences.getString(PREF_DEFAULT_SUPPLIER_EMAIL, "")
        )
        return x
    }

    override fun updateSettings(updatedSettings: SettingsSet) {
        with(securePreferences.edit()){
            updatedSettings.shouldHideSensitiveData?.let{
                this.putBoolean(PREF_SHOULD_HIDE_SENSITIVE_DATA, it)
            }
            updatedSettings.shouldUseDefaults?.let{
                this.putBoolean(PREF_SHOULD_USE_DEFAULTS, it)
            }
            updatedSettings.shouldUseDataSharing?.let{
                this.putBoolean(PREF_SHOULD_USE_DATA_SHARING, it)
            }
            updatedSettings.defaultSupplierName?.let{
                this.putString(PREF_DEFAULT_SUPPLIER_NAME, it)
            }
            updatedSettings.defaultSupplierPhone?.let{
                this.putString(PREF_DEFAULT_SUPPLIER_PHONE, it)
            }
            updatedSettings.defaultSupplierEmail?.let{
                this.putString(PREF_DEFAULT_SUPPLIER_EMAIL, it)
            }
            commit()
        }
    }

    companion object{
        const val PREF_FILENAME = "InventoryPreferences"
        private const val PREF_SHOULD_HIDE_SENSITIVE_DATA = "shouldHideSensitiveData"
        private const val PREF_SHOULD_USE_DEFAULTS = "shouldUseDefaults"
        private const val PREF_SHOULD_USE_DATA_SHARING = "shouldUseDataSharing"
        private const val PREF_DEFAULT_SUPPLIER_NAME = "defaultSupplierName"
        private const val PREF_DEFAULT_SUPPLIER_PHONE = "defaultSupplierPhone"
        private const val PREF_DEFAULT_SUPPLIER_EMAIL = "defaultSupplierEmail"
    }
}
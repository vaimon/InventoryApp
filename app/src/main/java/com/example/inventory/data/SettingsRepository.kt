package com.example.inventory.data

interface SettingsRepository {
    fun getSettings(): SettingsSet

    fun updateSettings(updatedSettings: SettingsSet)
    fun isDataSharingEnabled(): Boolean
}
package com.example.inventory.ui.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.inventory.data.SettingsRepository
import com.example.inventory.data.SettingsSet

class SettingsViewModel(private val settingsRepository: SettingsRepository): ViewModel() {
    val settingsUiState = mutableStateOf(settingsRepository.getSettings().toSettingsUiState())

    fun onSendDataSwitchCheckedChanged(newValue: Boolean){
        settingsUiState.value = settingsUiState.value.copy(sendDataSwitchChecked = newValue)
        settingsRepository.updateSettings(SettingsSet(shouldUseDataSharing = newValue))
    }

    fun onHideSensitiveDataCheckedChanged(newValue: Boolean){
        settingsUiState.value = settingsUiState.value.copy(hideSensitiveDataChecked = newValue)
        settingsRepository.updateSettings(SettingsSet(shouldHideSensitiveData = newValue))
    }

    fun onUseDefaultValuesCheckedChanged(newValue: Boolean){
        settingsUiState.value = settingsUiState.value.copy(useDefaultValuesChecked = newValue)
        settingsRepository.updateSettings(SettingsSet(shouldUseDefaults = newValue))
    }

    fun onDefaultSupplierNameChanged(newValue: String){
        settingsUiState.value = settingsUiState.value.copy(defaultSupplierName = newValue)
        settingsRepository.updateSettings(SettingsSet(defaultSupplierName = newValue))
    }

    fun onDefaultSupplierEmailChanged(newValue: String){
        settingsUiState.value = settingsUiState.value.copy(defaultSupplierEmail = newValue)
        settingsRepository.updateSettings(SettingsSet(defaultSupplierEmail = newValue))
    }

    fun onDefaultSupplierPhoneChanged(newValue: String){
        settingsUiState.value = settingsUiState.value.copy(defaultSupplierPhone = newValue)
        settingsRepository.updateSettings(SettingsSet(defaultSupplierPhone = newValue))
    }
}

data class SettingsUiState(
    val sendDataSwitchChecked: Boolean,
    val hideSensitiveDataChecked: Boolean,
    val useDefaultValuesChecked: Boolean,

    var defaultSupplierName: String,
    var defaultSupplierEmail: String,
    var defaultSupplierPhone: String
)

fun SettingsSet.toSettingsUiState(): SettingsUiState{
    return SettingsUiState(
        sendDataSwitchChecked = shouldUseDataSharing ?: false,
        hideSensitiveDataChecked = shouldHideSensitiveData ?: false,
        useDefaultValuesChecked = shouldUseDefaults ?: false,
        defaultSupplierName = defaultSupplierName ?: "",
        defaultSupplierPhone = defaultSupplierPhone ?: "",
        defaultSupplierEmail = defaultSupplierEmail ?: ""
    )
}
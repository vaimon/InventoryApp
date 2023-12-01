package com.example.inventory.ui.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.inventory.data.SettingsRepository

class SettingsViewModel(val settingsRepository: SettingsRepository): ViewModel() {
    val settingsUiState = mutableStateOf<SettingsUiState>(SettingsUiState())

    fun onSendDataSwitchCheckedChanged(newValue: Boolean){
        settingsUiState.value = settingsUiState.value.copy(sendDataSwitchChecked = newValue)
    }

    fun onHideSensitiveDataCheckedChanged(newValue: Boolean){
        settingsUiState.value = settingsUiState.value.copy(hideSensitiveDataChecked = newValue)
    }

    fun onUseDefaultValuesCheckedChanged(newValue: Boolean){
        settingsUiState.value = settingsUiState.value.copy(useDefaultValuesChecked = newValue)
    }

    fun onDefaultSupplierNameChanged(newValue: String){
        settingsUiState.value = settingsUiState.value.copy(defaultSupplierName = newValue)
    }

    fun onDefaultSupplierEmailChanged(newValue: String){
        settingsUiState.value = settingsUiState.value.copy(defaultSupplierEmail = newValue)
    }

    fun onDefaultSupplierPhoneChanged(newValue: String){
        settingsUiState.value = settingsUiState.value.copy(defaultSupplierPhone = newValue)
    }
}

data class SettingsUiState(
    val sendDataSwitchChecked: Boolean = false,
    val hideSensitiveDataChecked: Boolean = false,
    val useDefaultValuesChecked: Boolean = false,

    var defaultSupplierName: String = "",
    var defaultSupplierEmail: String = "",
    var defaultSupplierPhone: String = ""
)
package com.example.inventory.ui.settings

import androidx.lifecycle.ViewModel
import com.example.inventory.data.SettingsRepository

class SettingsViewModel(val settingsRepository: SettingsRepository): ViewModel() {

}

data class SettingsUiState(val sendDataSwitchChecked: Boolean)
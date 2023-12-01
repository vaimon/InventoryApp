package com.example.inventory.ui.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.item.ItemDetails
import com.example.inventory.ui.item.ItemDetailsUiState
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme

object SettingsDestination : NavigationDestination {
    override val route = "settings"
    override val titleRes = R.string.settings
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val settingsUiState by viewModel.settingsUiState

    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(SettingsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        },

        ) { innerPadding ->
        Column(Modifier.padding(innerPadding)){
            SettingItem(
                name = stringResource(R.string.data_sharing),
                isChecked = settingsUiState.sendDataSwitchChecked,
                onCheckedChange = viewModel::onSendDataSwitchCheckedChanged
            )
            SettingItem(
                name = stringResource(R.string.use_default_values),
                isChecked = settingsUiState.useDefaultValuesChecked,
                onCheckedChange = viewModel::onUseDefaultValuesCheckedChanged
            ){
                DefaultValueInputField(
                    value = settingsUiState.defaultSupplierName,
                    onValueChange = viewModel::onDefaultSupplierNameChanged,
                    label = stringResource(R.string.supplier_name)
                )
                DefaultValueInputField(
                    value = settingsUiState.defaultSupplierEmail,
                    onValueChange = viewModel::onDefaultSupplierEmailChanged,
                    label = stringResource(R.string.supplier_email)
                )
                DefaultValueInputField(
                    value = settingsUiState.defaultSupplierPhone,
                    onValueChange = viewModel::onDefaultSupplierPhoneChanged,
                    label = stringResource(R.string.supplier_phone),
                )
            }
            SettingItem(
                name = stringResource(R.string.hide_sensitive_data),
                isChecked = settingsUiState.hideSensitiveDataChecked,
                onCheckedChange = viewModel::onHideSensitiveDataCheckedChanged
            )
        }
    }
}

@Composable
fun DefaultValueInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
){
    OutlinedTextField(
        shape = RoundedCornerShape(32.0.dp),
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun SettingItem(
    name: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    content: (@Composable ColumnScope.() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(32.0.dp),
        modifier = Modifier.padding(8.0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.0.dp, vertical = 4.0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, true)
            )
            Spacer(modifier = Modifier.width(16.0.dp))
            Switch(checked = isChecked, onCheckedChange = onCheckedChange)
        }
        if(isChecked && content != null){
            Column(content = content,
                modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.0.dp, vertical = 8.0.dp),)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    InventoryTheme {
        SettingItem(
            name = "Send something to someone",
            isChecked = true,
            onCheckedChange = {})
    }
}
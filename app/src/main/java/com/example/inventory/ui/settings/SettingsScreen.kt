package com.example.inventory.ui.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.R
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.item.ItemDetails
import com.example.inventory.ui.item.ItemDetailsUiState
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme
import com.example.inventory.utils.FieldValidator

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
            CenterAlignedTopAppBar(
                title = { Text(stringResource(SettingsDestination.titleRes)) },
                modifier = modifier,
                navigationIcon = {
                        IconButton(onClick = navigateBack) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_button)
                            )
                        }
                }
            )
        },

        ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            SettingItem(
                name = stringResource(R.string.data_sharing),
                isChecked = settingsUiState.sendDataSwitchChecked,
                onCheckedChange = viewModel::onSendDataSwitchCheckedChanged
            )
            SettingItem(
                name = stringResource(R.string.use_default_values),
                isChecked = settingsUiState.useDefaultValuesChecked,
                onCheckedChange = viewModel::onUseDefaultValuesCheckedChanged
            ) {
                DefaultValueInputField(
                    value = settingsUiState.defaultSupplierName,
                    onValueChange = viewModel::onDefaultSupplierNameChanged,
                    label = stringResource(R.string.supplier_name),
                    validator = FieldValidator::validateName,
                    keyboardType = KeyboardType.Text
                )
                Spacer(Modifier.height(8.0.dp))
                DefaultValueInputField(
                    value = settingsUiState.defaultSupplierEmail,
                    onValueChange = viewModel::onDefaultSupplierEmailChanged,
                    label = stringResource(R.string.supplier_email),
                    validator = FieldValidator::validateEmail,
                    keyboardType = KeyboardType.Email
                )
                Spacer(Modifier.height(8.0.dp))
                DefaultValueInputField(
                    value = settingsUiState.defaultSupplierPhone,
                    onValueChange = viewModel::onDefaultSupplierPhoneChanged,
                    label = stringResource(R.string.supplier_phone),
                    validator = FieldValidator::validatePhoneNumber,
                    keyboardType = KeyboardType.Phone
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DefaultValueInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    validator: (String) -> Boolean,
    keyboardType: KeyboardType
) {
    var isFieldEditable by remember {
        mutableStateOf(false)
    }

    var mutableValue by remember {
        mutableStateOf(value)
    }

    OutlinedTextField(
        shape = RoundedCornerShape(32.0.dp),
        value = mutableValue,
        onValueChange = {
            mutableValue = it
        },
        label = { Text(label) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        readOnly = !isFieldEditable,
        isError = !(validator(mutableValue) || mutableValue.isBlank()),
        trailingIcon = {
            Icon(
                imageVector = if (isFieldEditable)
                    if ((validator(mutableValue) || mutableValue.isBlank()))
                        Icons.Filled.Check
                    else Icons.Filled.Close
                else Icons.Filled.Edit,
                contentDescription = stringResource(R.string.edit),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        if ((validator(mutableValue) || mutableValue.isBlank())) {
                            onValueChange(mutableValue)
                        } else {
                            mutableValue = value
                        }
                        isFieldEditable = !isFieldEditable
                    }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
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
        if (isChecked && content != null) {
            Column(
                content = content,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.0.dp, vertical = 8.0.dp),
            )
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
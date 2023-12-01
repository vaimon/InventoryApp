package com.example.inventory.ui.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.navigation.NavigationDestination

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
//    val settingsUiState by viewModel.settingsUiState.collectAsState()

    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(SettingsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        },

    ) { innerPadding ->
        SettingsBody(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun SettingsBody(
    modifier: Modifier
){

}
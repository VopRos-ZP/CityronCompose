package com.vopros.cityron.ui.components.toolbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ToolbarViewModel @Inject constructor() : ViewModel() {

    var title by mutableStateOf("")

    var isShowBack by mutableStateOf(false)

    var navController by mutableStateOf<NavController?>(null)
}
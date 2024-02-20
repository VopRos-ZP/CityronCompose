package com.vopros.cityron.ui.components.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.vopros.cityron.destinations.FindControllerScreenDestination
import com.vopros.cityron.destinations.MainScreenDestination

enum class BottomBarItem(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    val label: String
) {
    Main(MainScreenDestination, Icons.Default.Home, "Главная"),
    FindController(FindControllerScreenDestination, Icons.Default.Info, "Найти контроллер")
}
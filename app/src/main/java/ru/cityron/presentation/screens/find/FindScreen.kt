package ru.cityron.presentation.screens.find

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import ru.cityron.domain.model.Controller
import ru.cityron.presentation.components.DrawerScaffold
import ru.cityron.presentation.navigation.Screen
import ru.cityron.presentation.navigation.rememberNavigationState

@Composable
fun FindScreen(
    onClick: () -> Unit,
    onAddClick: (Controller) -> Unit,
    onCustomClick: () -> Unit,
) {
    val viewModel: FindViewModel = hiltViewModel()
    val infoList by viewModel.infoList.collectAsState()
    DrawerScaffold(title = "Поиск контроллеров", onClick = onClick) {
        FindScreenContent(infoList, onAddClick, onCustomClick)
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchInfoList()
    }
}

@Composable
private fun FindScreenContent(
    infoList: Map<Controller, Boolean>,
    onClick: (Controller) -> Unit,
    onCustomClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        when (infoList) {
            emptyMap<Controller, Boolean>() -> Text(text = "Контроллеры не найдены")
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp),
            ) {
                infoList.map { (controller, added) ->
                    item {
                        ControllerItem(
                            controller = controller,
                            added = added
                        ) { onClick(controller) }
                    }
                }
                item {
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onCustomClick,
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.secondary),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Color.Transparent,
                            contentColor = MaterialTheme.colors.onPrimary
                        )
                    ) {
                        Text(
                            modifier = Modifier.fillMaxSize().padding(vertical = 14.dp),
                            text = "Добавить контроллер вручную",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ControllerItem(
    controller: Controller,
    added: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = controller.name)
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = onClick,
                enabled = !added,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onBackground
                )
            ) {
                Text(text = "Добавить")
            }
        }
    }
}

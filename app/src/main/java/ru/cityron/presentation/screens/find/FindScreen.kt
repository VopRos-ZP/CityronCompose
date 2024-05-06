package ru.cityron.presentation.screens.find

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.domain.model.Controller

@Composable
fun FindScreen(onClick: () -> Unit) {
    val viewModel: FindViewModel = hiltViewModel()
    val infoList by viewModel.infoList.collectAsState()
    Scaffold(
        topBar = { FindTopBar(onClick) },
    ) {
        FindScreenContent(it, infoList, viewModel::addController)
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchInfoList()
    }
}

@Composable
private fun FindScreenContent(
    paddingValues: PaddingValues,
    infoList: Map<Controller, Boolean>,
    onClick: (Controller) -> Unit
) {
    Box(
        modifier = Modifier.padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        when (infoList) {
            emptyMap<Controller, Boolean>() -> Text(text = "Контроллеры не найдены")
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(20.dp)
            ) {
                infoList.map { (controller, added) ->
                    item {
                        ControllerItem(
                            controller = controller,
                            added = added
                        ) { onClick(controller) }
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
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = controller.name)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onClick,
                enabled = !added
            ) {
                Text(text = "Добавить")
            }
        }
    }
}

@Composable
fun FindTopBar(onClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "menu"
                )
            }
        },
        title = { Text(text = "Поиск контроллеров") }
    )
}
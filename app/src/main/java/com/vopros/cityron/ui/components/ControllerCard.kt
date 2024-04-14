package com.vopros.cityron.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.cityron.core.domain.model.Controller

@Composable
fun ControllerCard(
    item: Controller,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = item.name)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun ControllerCard_Preview() {
    ControllerCard(item = Controller(
        id = 0,
        name = "m3#asdfg",
        ipAddress = "",
        idCpu = ""
    )) {}
}
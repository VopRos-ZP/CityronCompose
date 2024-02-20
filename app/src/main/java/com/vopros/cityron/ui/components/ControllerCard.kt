package com.vopros.cityron.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vopros.cityron.controller.ControllerItem

@Composable
fun ControllerCard(
    item: ControllerItem,
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
    ControllerCard(item = ControllerItem(
        name = "m3#asdfg",
        ipAddress = "",
        idCpu = ""
    )) {}
}
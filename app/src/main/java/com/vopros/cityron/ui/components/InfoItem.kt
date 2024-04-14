package com.vopros.cityron.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.cityron.core.domain.model.Info

@Composable
fun InfoItem(info: Info) {
    Card {
        Row {
            Text(text = "${info.devName}#${info.idUsr}")
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { /*TODO: Нажатие на "Добавить" */ }) {
                Text(text = "Добавить")
            }
        }
    }
}

@Preview
@Composable
fun InfoItem_Preview() {
    InfoItem(info = Info(
        "info",
        0, "afeadc",
        "fasfewad",
        "m3",
        "",
        0,
        0,
        "192.168.1.102",
        "255.255.255.0",
        "192.168.1.100"
    ))
}
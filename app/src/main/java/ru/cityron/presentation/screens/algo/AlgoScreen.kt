package ru.cityron.presentation.screens.algo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import ru.cityron.R
import ru.cityron.presentation.components.BackScaffold

@Composable
fun AlgoScreen(
    onClick: () -> Unit,
    onTimingsClick: () -> Unit,
    onFan1Click: () -> Unit,
    onFan2Click: () -> Unit,
    onPi1Click: () -> Unit,
    onPi2Click: () -> Unit,
    onWaterClick: () -> Unit,
    onElectricClick: () -> Unit,
    onOtherClick: () -> Unit,
) {
    val onClickList = listOf(
        onTimingsClick, onFan1Click, onFan2Click,
        onPi1Click, onPi2Click, onWaterClick,
        onElectricClick, onOtherClick
    )
    val types = stringArrayResource(id = R.array.algo_types_m3).zip(onClickList)
    BackScaffold(
        title = "Алгоритм",
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            LazyColumn(
                modifier = Modifier.padding(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp)
            ) {
                items(types) { (text, onClick) ->
                    AlgoItem(
                        text = text,
                        onClick = onClick
                    )
                }
            }
        }
    }
}

@Composable
fun AlgoItem(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.primary)
            .clickable { onClick() }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}
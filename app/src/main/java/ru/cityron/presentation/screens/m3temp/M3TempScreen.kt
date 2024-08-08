package ru.cityron.presentation.screens.m3temp

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.domain.utils.Temp
import ru.cityron.presentation.components.FanSlider
import ru.cityron.presentation.components.Thermostat
import ru.cityron.presentation.screens.m3tabs.M3ViewModel
import ru.cityron.ui.theme.Green

@Composable
fun M3TempScreen(
    onAlertsClick: () -> Unit,
    onSchedulerClick: () -> Unit,
    viewModel: M3ViewModel = hiltViewModel()
) {
    val errorColor = MaterialTheme.colors.error
    val onColor = Green
    val offColor = MaterialTheme.colors.primary

    val state by viewModel.state().collectAsState()
    val statusColor by remember(state) {
        mutableStateOf(
            when {
                state.isShowAlarms -> errorColor
                state.isPowerOn -> onColor
                else -> offColor
            }
        )
    }
    val statusOffset = (-5).dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(contentAlignment = Alignment.Center) {
            Thermostat(
                modifier = Modifier.size(275.dp),
                value = state.temp,
                minValue = 50,
                maxValue = 450,
                step = 50,
                onPositionChange = {
                    viewModel.intent(M3TempViewIntent.OnTempChange(it))
                }
            )
            Text(
                buildAnnotatedString {
                    val (div, mod) = Temp.toGrade(state.tempPv).split(".")
                    withStyle(SpanStyle(fontSize = MaterialTheme.typography.h2.fontSize)) {
                        append(div)
                    }
                    withStyle(SpanStyle(fontSize = MaterialTheme.typography.h3.fontSize)) {
                        append(".$mod")
                    }
                },
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.h2
            )
            Icon(
                painter = painterResource(id = R.drawable.grade),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .offset(x = 75.dp, y = (-35).dp),
                tint = MaterialTheme.colors.primaryVariant
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FanSlider(
                values = listOf(1, 2, 3, 4, 5),
                value = state.fan,
                onFinishChange = { viewModel.intent(M3TempViewIntent.OnFanChange(it)) }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${state.fan}",
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.h1
                )
                Icon(
                    painter = painterResource(id = R.drawable.fan),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .offset(statusOffset, statusOffset)
                        .background(
                            color = statusColor,
                            shape = RoundedCornerShape(percent = 100)
                        )
                )
                Button(
                    shape = RoundedCornerShape(percent = 100),
                    modifier = Modifier.size(90.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (state.isPowerOn) MaterialTheme.colors.primary
                        else MaterialTheme.colors.onPrimary
                    ),
                    onClick = {
                        viewModel.intent(M3TempViewIntent.OnIsShowOnOffDialogChange(true))
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.on_off),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = if (state.isPowerOn) MaterialTheme.colors.primaryVariant
                        else MaterialTheme.colors.primary
                    )
                }
            }
            Button(
                shape = RoundedCornerShape(percent = 100),
                modifier = Modifier.size(90.dp),
                onClick = onSchedulerClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
        }
        AnimatedContent(targetState = state.isShowAlarms, label = "") {
            val (bg, content) = when (it) {
                true -> MaterialTheme.colors.error to MaterialTheme.colors.onBackground
                else -> MaterialTheme.colors.background to Color.Transparent
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClick = onAlertsClick,
                enabled = it,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = bg,
                    contentColor = content,
                    disabledContentColor = content,
                    disabledBackgroundColor = bg
                ),
                contentPadding = PaddingValues(20.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.danger),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Аварии",
                        style = MaterialTheme.typography.body1
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
    if (state.isShowOnOffDialog) {
        val text = if (state.isPowerOn) "выключить" else "включить"
        AlertDialog(
            shape = RoundedCornerShape(4.dp),
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            text = {
                Text(
                    text = "Вы уверены что хотите\n$text контроллер?",
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.body1
                )
            },
            onDismissRequest = {
                viewModel.intent(M3TempViewIntent.OnIsShowOnOffDialogChange(false))
            },
            confirmButton = {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colors.primaryVariant
                    ),
                    onClick = {
                        viewModel.intent(M3TempViewIntent.OnConfirmOnOffClick)
                        viewModel.intent(M3TempViewIntent.OnIsShowOnOffDialogChange(false))
                    }
                ) {
                    Text(
                        text = text.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase() else it.toString()
                        },
                        style = MaterialTheme.typography.button
                    )
                }
            },
            dismissButton = {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colors.onPrimary
                    ),
                    onClick = { viewModel.intent(M3TempViewIntent.OnIsShowOnOffDialogChange(false)) }
                ) {
                    Text(
                        text = "Отмена",
                        style = MaterialTheme.typography.button
                    )
                }
            }
        )
    }
    LaunchedEffect(Unit) {
        viewModel.intent(M3TempViewIntent.Launch)
    }
}
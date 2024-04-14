package com.vopros.cityron.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.vopros.cityron.ui.theme.LocalToolbar

/**
 * Общая функция для экрана
 * @param title Заголовок в Toolbar
 * @param isShowBack Показывать ли кнопку назад в Toolbar
 * @param navController Экземпляр контроллера для навигации (кнопки назад)
 * @param content Содержимое экрана
 * **/
@Composable
fun Screen(
    title: String,
    isShowBack: Boolean = false,
    navController: NavController? = null,
    content: @Composable BoxScope.() -> Unit
) {
//    val viewModel = LocalToolbar.current
//    viewModel.title = title
//    viewModel.isShowBack = isShowBack
//    viewModel.navController = navController
   Box(
       modifier = Modifier.fillMaxSize(),
       contentAlignment = Alignment.Center,
       content = content
   )
}
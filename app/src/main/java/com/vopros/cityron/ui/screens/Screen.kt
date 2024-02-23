package com.vopros.cityron.ui.screens

import androidx.compose.runtime.Composable
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
    content: @Composable () -> Unit
) {
    val viewModel = LocalToolbar.current
    viewModel.title = title
    viewModel.isShowBack = isShowBack
    viewModel.navController = navController
    content()
}
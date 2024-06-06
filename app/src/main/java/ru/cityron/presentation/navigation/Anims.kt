package ru.cityron.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

private val enterTransition: @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        fadeIn(
            animationSpec = tween(300, easing = LinearEasing)
        ) + slideIntoContainer(
            animationSpec = tween(300, easing = EaseIn),
            towards = AnimatedContentTransitionScope.SlideDirection.Start
        )
    }

private val exitTransition: @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        fadeOut(
            animationSpec = tween(300, easing = LinearEasing)
        )
    }

private val popEnterTransition: @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        fadeIn(
            animationSpec = tween(300, easing = LinearEasing)
        )
    }

private val popExitTransition: @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        fadeOut(
            animationSpec = tween(300, easing = LinearEasing)
        ) + slideOutOfContainer(
            animationSpec = tween(300, easing = EaseOut),
            towards = AnimatedContentTransitionScope.SlideDirection.End
        )
    }

fun NavGraphBuilder.slideInOutComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = enterTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        exitTransition = exitTransition,
        arguments = arguments,
        content = content
    )
}

@Composable
fun SlideInOutNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        route = route,
        builder = builder
    )
}
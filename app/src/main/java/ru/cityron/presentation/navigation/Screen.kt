package ru.cityron.presentation.navigation

sealed class Screen(
    val route: String
) {

    data object Blank : Screen(ROUTE_BLANK)
    data object Find : Screen(ROUTE_FIND)

    data object Alerts : Screen(ROUTE_ALERTS)
    data object Schedulers : Screen(ROUTE_SCHEDULERS)

    data object M3Tabs : Screen(ROUTE_M3_TABS)

    companion object {
        const val ROUTE_BLANK = "blank"
        const val ROUTE_FIND = "find"
        const val ROUTE_ALERTS = "alerts"
        const val ROUTE_SCHEDULERS = "alerts"

        const val ROUTE_ATLAS = "atlas"
        const val ROUTE_M3_TABS = "m3_tabs"
    }

}
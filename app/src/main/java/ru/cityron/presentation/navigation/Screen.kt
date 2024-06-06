package ru.cityron.presentation.navigation

sealed class Screen(
    val route: String
) {

    data object Blank : Screen(ROUTE_BLANK)
    data object FindFlow : Screen(ROUTE_FIND_FLOW)
    data object Find : Screen(ROUTE_FIND)
    data object AddController : Screen(ROUTE_ADD_CONTROLLER)
    data object AddCustom : Screen(ROUTE_ADD_CUSTOM)
    data object ChangeName : Screen(ROUTE_CHANGE_NAME)
    data object Auth : Screen(ROUTE_CHANGE_NAME)
    data object Algo : Screen(ROUTE_CHANGE_NAME)
    data object Alarms : Screen(ROUTE_CHANGE_NAME)
    data object Controller : Screen(ROUTE_CHANGE_NAME)

    data object Alerts : Screen(ROUTE_ALERTS)
    data object Schedulers : Screen(ROUTE_SCHEDULERS)
    data class Task(val id: Int) : Screen("$ROUTE_SCHEDULERS/$id")

    data object Settings : Screen(ROUTE_SETTINGS)

    data object M3Tabs : Screen(ROUTE_M3_TABS)

    companion object {
        const val ROUTE_BLANK = "blank"
        const val ROUTE_FIND_FLOW = "find_flow"
        const val ROUTE_FIND = "find"
        const val ROUTE_ADD_CONTROLLER = "add_controller"
        const val ROUTE_ADD_CUSTOM = "add_custom"
        const val ROUTE_CHANGE_NAME = "change_name"
        const val ROUTE_ALERTS = "alerts"
        const val ROUTE_SCHEDULERS = "schedulers"
        const val ROUTE_SETTINGS = "settings"

        const val ROUTE_ATLAS = "atlas"
        const val ROUTE_M3_TABS = "m3_tabs"
    }

}
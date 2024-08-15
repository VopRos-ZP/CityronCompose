package ru.cityron.presentation.navigation

sealed class Screen(val route: String) {

    data object Blank : Screen(ROUTE_BLANK)
    data object FindFlow : Screen(ROUTE_FIND_FLOW)
    data object Find : Screen(ROUTE_FIND)
    data class AddController(val role: String) : Screen("$ROUTE_ADD_CONTROLLER/$role")
    data object AddCustom : Screen(ROUTE_ADD_CUSTOM)
    data class AuthRole(val role: String) : Screen("$ROUTE_AUTH/$role")

    data object ChangeName : Screen(ROUTE_CHANGE_NAME)
    data object Auth : Screen(ROUTE_AUTH)
    data object Algo : Screen(ROUTE_ALGO)
    data object Alarms : Screen(ROUTE_ALARMS)
    data object Controller : Screen(ROUTE_CONTROLLER)
    data object Filters : Screen(ROUTE_FILTERS)

    data object Alerts : Screen(ROUTE_ALERTS)
    data object Schedulers : Screen(ROUTE_SCHEDULERS)
    data class Task(val id: Int) : Screen("$ROUTE_SCHEDULERS/$id")
    data class EditAlarm(val id: Int) : Screen("$ROUTE_ALARMS/$id")

    data object Settings : Screen(ROUTE_SETTINGS)

    data object M3Tabs : Screen(ROUTE_M3_TABS)

    data object AlgoTimings : Screen(ROUTE_ALGO_TIMINGS)
    data object AlgoFan1 : Screen(ROUTE_ALGO_FAN1)
    data object AlgoFan2 : Screen(ROUTE_ALGO_FAN2)
    data object AlgoPi1 : Screen(ROUTE_ALGO_PI1)
    data object AlgoPi2 : Screen(ROUTE_ALGO_PI2)
    data object AlgoElectric : Screen(ROUTE_ALGO_ELECTRIC)
    data object AlgoWater : Screen(ROUTE_ALGO_WATER)
    data object AlgoOther : Screen(ROUTE_ALGO_OTHER)

    data object ControllerDatetime : Screen(ROUTE_CONTROLLER_DATETIME)
    data object ControllerEth : Screen(ROUTE_CONTROLLER_ETH)
    data object ControllerWeb : Screen(ROUTE_CONTROLLER_WEB)
    data object ControllerMetric : Screen(ROUTE_CONTROLLER_METRIC)

    companion object {
        const val ROUTE_BLANK = "blank"
        const val ROUTE_FIND_FLOW = "find_flow"
        const val ROUTE_FIND = "find"
        const val ROUTE_ADD_CONTROLLER = "add_controller"
        const val ROUTE_ADD_CUSTOM = "add_custom"
        const val ROUTE_AUTH = "auth"
        const val ROUTE_CHANGE_NAME = "change_name"
        const val ROUTE_ALERTS = "alerts"
        const val ROUTE_ALARMS = "alarms"
        const val ROUTE_ALGO = "algo"
        const val ROUTE_SCHEDULERS = "schedulers"
        const val ROUTE_CONTROLLER = "controller"
        const val ROUTE_SETTINGS = "settings"
        const val ROUTE_FILTERS = "filters"

        const val ROUTE_ALGO_TIMINGS = "timings"
        const val ROUTE_ALGO_FAN1 = "fan1"
        const val ROUTE_ALGO_FAN2 = "fan2"
        const val ROUTE_ALGO_PI1 = "pi1"
        const val ROUTE_ALGO_PI2 = "pi2"
        const val ROUTE_ALGO_ELECTRIC = "electric"
        const val ROUTE_ALGO_WATER = "water"
        const val ROUTE_ALGO_OTHER = "other"

        const val ROUTE_CONTROLLER_DATETIME = "datetime"
        const val ROUTE_CONTROLLER_ETH = "eth"
        const val ROUTE_CONTROLLER_WEB = "web"
        const val ROUTE_CONTROLLER_METRIC = "metric"

        const val ROUTE_ATLAS = "atlas"
        const val ROUTE_M3_TABS = "m3_tabs"
    }

}
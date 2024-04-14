package com.vopros.cityron.ui.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@NavGraph
@RootNavGraph
annotation class DrawerNavGraph(val start: Boolean = false)

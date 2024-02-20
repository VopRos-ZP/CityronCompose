package com.vopros.cityron.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@NavGraph
@RootNavGraph
annotation class FindControllerNavGraph(val start: Boolean = false)

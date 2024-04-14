package com.vopros.cityron.ui.screens.findController

import ru.cityron.core.domain.model.Info

data class FindViewState(
    val isLoading: Boolean = false,
    val infoList: List<Pair<Info, Boolean>> = emptyList(),
    val message: String? = null
)

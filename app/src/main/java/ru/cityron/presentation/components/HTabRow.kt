package ru.cityron.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HTabRow(
    pagerState: PagerState,
    tabs: List<TabWithPage>
) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.primary
    ) {
        tabs.forEachIndexed { i, tab ->
            Tab(
                icon = {
                    Icon(
                        painter = painterResource(id = tab.icon),
                        contentDescription = null
                    )
                },
                text = { Text(text = tab.title, fontSize = 12.sp) },
                selected = pagerState.currentPage == i,
                onClick = { scope.launch { pagerState.animateScrollToPage(i) } }
            )
        }
    }
}
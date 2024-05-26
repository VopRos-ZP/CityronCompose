package ru.cityron.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.cityron.ui.theme.LightGrey

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HTabRow(
    pagerState: PagerState,
    tabs: List<TabWithPage>
) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.primary,
        indicator = {
            Spacer(
                Modifier
                    .tabIndicatorOffset(it[pagerState.currentPage])
                    .requiredWidth(60.dp)
                    .requiredHeight(5.dp)
                    .background(
                        color = MaterialTheme.colors.primaryVariant,
                        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                    )
            )
        },
        divider = { TabRowDefaults.Divider(color = MaterialTheme.colors.primaryVariant) }
    ) {
        tabs.forEachIndexed { i, tab ->
            val selected = pagerState.currentPage == i
            Tab(
                icon = {
                    Icon(
                        painter = painterResource(id = tab.icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (selected) MaterialTheme.colors.onBackground
                        else LightGrey
                    )
                },
                text = {
                    Text(
                        text = tab.title,
                        color = if (selected) MaterialTheme.colors.onBackground
                        else LightGrey
                    )
                },
                selected = selected,
                onClick = { scope.launch { pagerState.animateScrollToPage(i) } }
            )
        }
    }
}
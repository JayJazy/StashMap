package com.jayys.stashmap.feature.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.jayys.stashmap.core.designsystem.component.stash.StashBottomNavBar
import com.jayys.stashmap.core.designsystem.component.stash.StashNavItem
import com.jayys.stashmap.feature.main.nav.STASH_MAIN_NAV_ITEMS

/**
 * 앱 셸의 바텀 네비게이션
 *
 * 네비 모델([STASH_MAIN_NAV_ITEMS])을 DS 모델([StashNavItem])로 옮기고 탭 전환만 담당.
 * 생김새·인셋 처리는 [StashBottomNavBar] 가 가짐
 */
@Composable
fun MainBottomBar(
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier
) {
    val current = backStack.lastOrNull()

    StashBottomNavBar(
        items = STASH_MAIN_NAV_ITEMS.map { item ->
            StashNavItem(
                icon = ImageVector.vectorResource(item.iconRes),
                label = stringResource(item.labelRes),
                selected = current == item.route,
                onClick = {
                    if (backStack.lastOrNull() != item.route) {
                        backStack.clear()
                        backStack.add(item.route)
                    }
                }
            )
        },
        modifier = modifier
    )
}

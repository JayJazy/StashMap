package com.jayys.stashmap.feature.main.nav

import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import com.jayys.stashmap.feature.home.HomeRoute
import com.jayys.stashmap.feature.main.R
import com.jayys.stashmap.feature.profile.navigation.ProfileRoute
import com.jayys.stashmap.feature.stash.StashRoute

/**
 * 바텀 네비게이션 항목.
 *
 * 라벨을 [String]이 아니라 리소스 ID로 보관한다.
 * [STASH_MAIN_NAV_ITEMS]는 최상위 프로퍼티라 프로세스 수명 동안 한 번만 초기화되므로,
 * Configuration에 의존하는 값(문자열·치수·색)을 담아 두면 언어를 바꿔도 그 값이 그대로 남는다.
 * ID만 들고 있다가 컴포지션 시점에 `stringResource`로 해석해야 현재 Locale이 반영된다.
 */
data class StashMainNavItem(
    val route: NavKey,
    @StringRes val labelRes: Int
)

val STASH_MAIN_NAV_ITEMS = listOf(
    StashMainNavItem(HomeRoute, R.string.nav_home),
    StashMainNavItem(StashRoute, R.string.nav_stash),
    StashMainNavItem(ProfileRoute, R.string.nav_profile)
)

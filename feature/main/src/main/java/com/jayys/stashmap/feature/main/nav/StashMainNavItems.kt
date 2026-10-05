package com.jayys.stashmap.feature.main.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import com.jayys.stashmap.core.designsystem.R as DesignSystemR
import com.jayys.stashmap.feature.home.HomeRoute
import com.jayys.stashmap.feature.main.R
import com.jayys.stashmap.feature.profile.navigation.ProfileRoute
import com.jayys.stashmap.feature.stash.StashRoute

/**
 * 바텀 네비게이션 항목
 *
 * 라벨·아이콘은 값이 아니라 리소스 ID 로 보관
 * [STASH_MAIN_NAV_ITEMS] 는 최상위 프로퍼티 → 프로세스 수명 동안 한 번만 초기화
 * Configuration 의존 값(문자열·치수·색)을 담으면 언어를 바꿔도 그대로 남음
 * ID 만 들고 컴포지션 시점에 `stringResource`/`vectorResource` 로 해석해야 현재 Locale·테마 반영
 */
data class StashMainNavItem(
    val route: NavKey,
    @StringRes val labelRes: Int,
    @DrawableRes val iconRes: Int
)

val STASH_MAIN_NAV_ITEMS = listOf(
    StashMainNavItem(HomeRoute, R.string.nav_home, DesignSystemR.drawable.ic_home),
    StashMainNavItem(StashRoute, R.string.nav_stash, DesignSystemR.drawable.ic_utensils),
    StashMainNavItem(ProfileRoute, R.string.nav_profile, DesignSystemR.drawable.ic_user)
)

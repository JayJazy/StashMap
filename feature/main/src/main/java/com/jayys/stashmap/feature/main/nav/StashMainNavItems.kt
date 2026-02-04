package com.jayys.stashmap.feature.main.nav

import androidx.navigation3.runtime.NavKey
import com.jayys.stashmap.feature.home.HomeRoute
import com.jayys.stashmap.feature.profile.ProfileRoute
import com.jayys.stashmap.feature.stash.StashRoute

data class StashMainNavItem(
    val route: NavKey,
    val label: String
)

val STASH_MAIN_NAV_ITEMS = listOf(
    StashMainNavItem(HomeRoute, "Home"),
    StashMainNavItem(StashRoute, "맛집"),
    StashMainNavItem(ProfileRoute, "프로필")
)
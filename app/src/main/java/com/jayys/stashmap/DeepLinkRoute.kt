package com.jayys.stashmap

import android.net.Uri
import androidx.navigation3.runtime.NavKey
import com.jayys.stashmap.feature.home.HomeRoute
import com.jayys.stashmap.feature.profile.ProfileRoute
import com.jayys.stashmap.feature.stash.StashRoute

/**
 * intent.data URI를 분석하여 해당하는 route 객체를 반환합니다.
 * URI가 없거나 매칭되지 않으면 HomeRoute를 기본값으로 반환합니다.
 *
 * Supported paths:
 *   - /home        → HomeRoute
 *   - /stash       → StashRoute
 *   - /profile     → ProfileRoute
 */
fun resolveRoute(uri: Uri?): NavKey {
    val path = uri?.path?.trimStart('/') ?: return HomeRoute

    return when (path) {
        "home" -> HomeRoute
        "stash" -> StashRoute
        "profile" -> ProfileRoute
        else -> HomeRoute
    }
}
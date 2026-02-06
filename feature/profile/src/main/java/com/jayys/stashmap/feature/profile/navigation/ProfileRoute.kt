package com.jayys.stashmap.feature.profile.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object ProfileRoute : NavKey

@Serializable
data object InformationRoute : NavKey

@Serializable
data object LanguageRoute : NavKey

@Serializable
data object ThemeRoute : NavKey
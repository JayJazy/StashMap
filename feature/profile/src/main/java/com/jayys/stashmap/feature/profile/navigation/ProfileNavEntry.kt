package com.jayys.stashmap.feature.profile.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.jayys.stashmap.feature.information.InformationScreen
import com.jayys.stashmap.feature.language.screen.LanguageScreen
import com.jayys.stashmap.feature.profile.screen.ProfileScreen
import com.jayys.stashmap.feature.theme.ThemeScreen

fun EntryProviderScope<NavKey>.profileEntries(
    isDarkMode: MutableState<Boolean>,
    onDarkModeChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    backStack: NavBackStack<NavKey>
) {
    entry<ProfileRoute> { _ ->
        ProfileScreen(
            onLanguageClick = { backStack.add(LanguageRoute) },
            onThemeClick = { backStack.add(ThemeRoute) },
            onInformationClick = { backStack.add(InformationRoute) },
            onContactClick = { }
        )
    }

    entry<InformationRoute> { _ ->
        InformationScreen(onBack = onBack)
    }

    entry<LanguageRoute> { _ ->
        LanguageScreen(onBack = onBack)
    }

    entry<ThemeRoute> { _ ->
        ThemeScreen(
            onBack = onBack,
            isDarkMode = isDarkMode.value,
            onDarkModeChange = onDarkModeChange
        )
    }
}
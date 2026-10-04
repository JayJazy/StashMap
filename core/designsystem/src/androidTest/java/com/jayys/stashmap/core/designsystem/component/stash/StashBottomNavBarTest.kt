package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * [StashBottomNavBar] 계약 테스트
 *
 * 탭은 Role.Tab + selected 로 노출돼야 "3개 중 2번째 탭, 선택됨" 으로 읽힌다.
 * 인셋은 기기마다 다르니 테스트에서는 0 으로 고정
 */
class StashBottomNavBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // StashIcons 는 @Composable getter(vectorResource) 라 컴포지션 안에서만 읽을 수 있다
    @Composable
    private fun navItems(
        selectedIndex: Int,
        onClick: (Int) -> Unit = {},
    ) = listOf(
        StashNavItem(icon = StashIcons.Home, label = "홈", selected = selectedIndex == 0) { onClick(0) },
        StashNavItem(icon = StashIcons.Utensils, label = "맛집", selected = selectedIndex == 1) { onClick(1) },
        StashNavItem(icon = StashIcons.User, label = "프로필", selected = selectedIndex == 2) { onClick(2) },
    )

    @Test
    fun `모든_탭의_라벨이_보이고_탭_role을_갖는다`() {
        composeTestRule.setStashContent {
            StashBottomNavBar(items = navItems(selectedIndex = 0), windowInsets = WindowInsets(0))
        }

        listOf("홈", "맛집", "프로필").forEach { label ->
            composeTestRule.onNodeWithText(label)
                .assertIsDisplayed()
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab))
        }
    }

    @Test
    fun `selected인_탭만_선택_시맨틱을_갖는다`() {
        composeTestRule.setStashContent {
            StashBottomNavBar(items = navItems(selectedIndex = 1), windowInsets = WindowInsets(0))
        }

        composeTestRule.onNodeWithText("맛집").assertIsSelected()
        composeTestRule.onNodeWithText("홈").assertIsNotSelected()
        composeTestRule.onNodeWithText("프로필").assertIsNotSelected()
    }

    @Test
    fun `탭을_누르면_그_항목의_콜백이_온다`() {
        val clicked = mutableListOf<Int>()

        composeTestRule.setStashContent {
            StashBottomNavBar(
                items = navItems(selectedIndex = 0, onClick = { clicked += it }),
                windowInsets = WindowInsets(0),
            )
        }

        composeTestRule.onNodeWithText("프로필").performClick()
        composeTestRule.onNodeWithText("맛집").performClick()

        assertEquals(listOf(2, 1), clicked)
    }
}

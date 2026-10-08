package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.testing.labelOf
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * 컨테이너·표시용 컴포넌트 묶음 테스트
 * ([StashCard], [StashFab], [StashThumbnail], [StashInfoRow], [StashRestaurantCard])
 *
 * 공통 계약은 "onClick 이 null 이면 클릭 노드를 만들지 않는다" 와
 * "아이콘만 있는 자리에는 반드시 설명이 붙는다" 두 가지
 */
class StashCardComponentsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // ---------------------------------------------------------------- Card

    @Test
    fun `카드는_onClick을_주면_눌리고_콜백이_온다`() {
        var clicks = 0

        composeTestRule.setStashContent {
            StashCard(onClick = { clicks++ }) { StashText(text = "카드 내용") }
        }

        composeTestRule.onNodeWithText("카드 내용").assertHasClickAction().performClick()

        assertEquals(1, clicks)
    }

    @Test
    fun `카드는_onClick이_없으면_클릭되지_않는다`() {
        composeTestRule.setStashContent {
            StashCard { StashText(text = "카드 내용") }
        }

        composeTestRule.onNodeWithText("카드 내용").assertIsDisplayed().assertHasNoClickAction()
    }

    // ----------------------------------------------------------------- Fab

    @Test
    fun `FAB은_접근성_설명으로_찾히고_누르면_콜백이_온다`() {
        var clicks = 0

        composeTestRule.setStashContent {
            StashFab(onClick = { clicks++ }, contentDescription = "맛집 기록하기")
        }

        composeTestRule.onNodeWithContentDescription("맛집 기록하기")
            .assertHasClickAction()
            .performClick()

        assertEquals(1, clicks)
    }

    // ----------------------------------------------------------- Thumbnail

    @Test
    fun `썸네일은_이미지가_있을_때만_설명을_노출한다`() {
        composeTestRule.setStashContent {
            StashThumbnail(
                painter = ColorPainter(Color.Gray),
                contentDescription = "가게 사진",
                modifier = Modifier.testTag("thumb"),
            )
        }

        composeTestRule.onNodeWithContentDescription("가게 사진").assertIsDisplayed()
    }

    @Test
    fun `사진이_없으면_플레이스홀더만_그리고_설명을_붙이지_않는다`() {
        composeTestRule.setStashContent {
            StashThumbnail(contentDescription = "가게 사진", modifier = Modifier.testTag("thumb"))
        }

        composeTestRule.onNodeWithTag("thumb").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("가게 사진").assertDoesNotExist()
    }

    // ------------------------------------------------------------- InfoRow

    @Test
    fun `정보_행은_라벨과_값을_함께_그린다`() {
        composeTestRule.setStashContent {
            StashInfoRow(label = "방문일", value = "2026.05.21", icon = StashIcons.Calendar)
        }

        composeTestRule.onNodeWithText("방문일").assertIsDisplayed()
        composeTestRule.onNodeWithText("2026.05.21").assertIsDisplayed()
    }

    // ------------------------------------------------------- RestaurantCard

    @Test
    fun `맛집_카드는_이름_메타_메모를_그리고_평가_칩이_상태를_읽어준다`() {
        composeTestRule.setStashContent {
            StashRestaurantCard(
                name = "성수동 국밥",
                state = StashEvalState.Favorite,
                onClick = {},
                meta = "한식 · 성수동",
                memo = "국물이 진하다",
            )
        }

        composeTestRule.onNodeWithText("성수동 국밥").assertIsDisplayed()
        composeTestRule.onNodeWithText("한식 · 성수동").assertIsDisplayed()
        composeTestRule.onNodeWithText("국물이 진하다").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(labelOf(StashEvalState.Favorite))
            .assertIsDisplayed()
    }

    @Test
    fun `맛집_카드를_누르면_콜백이_온다`() {
        var clicks = 0

        composeTestRule.setStashContent {
            StashRestaurantCard(
                name = "성수동 국밥",
                state = StashEvalState.Average,
                onClick = { clicks++ },
            )
        }

        composeTestRule.onNodeWithText("성수동 국밥").performClick()

        assertEquals(1, clicks)
    }
}

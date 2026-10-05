package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jayys.stashmap.core.designsystem.testing.labelOf
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * [StashEvalStatGrid] 계약 테스트
 *
 * 칸 안의 아이콘 칩은 라벨을 바로 옆에 텍스트로 그리므로 장식(contentDescription = null)이어야 한다.
 * 칩이 기본 설명을 달면 TalkBack 이 라벨을 두 번 읽는다 — 컴파일로는 안 잡히는 자리
 */
class StashEvalStatGridTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun counts(state: StashEvalState) = when (state) {
        StashEvalState.Favorite -> 12
        StashEvalState.Average -> 5
        StashEvalState.Avoid -> 3
        StashEvalState.WantToTry -> 8
    }

    @Test
    fun `네_상태의_라벨과_개수가_모두_보인다`() {
        composeTestRule.setStashContent {
            StashEvalStatGrid(
                favoriteCount = 12,
                averageCount = 5,
                avoidCount = 3,
                wantToTryCount = 8,
            )
        }

        StashEvalState.entries.forEach { state ->
            composeTestRule.onNodeWithText(labelOf(state)).assertIsDisplayed()
            composeTestRule.onNodeWithText(counts(state).toString()).assertIsDisplayed()
        }
    }

    @Test
    fun `칸_안의_아이콘_칩은_장식이라_라벨이_접근성_설명으로_중복_노출되지_않는다`() {
        composeTestRule.setStashContent {
            StashEvalStatGrid(
                favoriteCount = 12,
                averageCount = 5,
                avoidCount = 3,
                wantToTryCount = 8,
                onStateClick = {},
            )
        }

        StashEvalState.entries.forEach { state ->
            // 라벨은 텍스트로 딱 한 번만 — 칩이 같은 문구를 설명으로 달면 안 된다
            composeTestRule.onAllNodesWithText(labelOf(state)).assertCountEquals(1)
            composeTestRule.onNodeWithContentDescription(labelOf(state)).assertDoesNotExist()
        }
    }

    @Test
    fun `칸을_누르면_그_칸의_평가_상태가_콜백으로_온다`() {
        val clicked = mutableListOf<StashEvalState>()

        composeTestRule.setStashContent {
            StashEvalStatGrid(
                favoriteCount = 12,
                averageCount = 5,
                avoidCount = 3,
                wantToTryCount = 8,
                onStateClick = { clicked += it },
            )
        }

        composeTestRule.onNodeWithText(labelOf(StashEvalState.Avoid)).performClick()
        composeTestRule.onNodeWithText(labelOf(StashEvalState.WantToTry)).performClick()

        assertEquals(listOf(StashEvalState.Avoid, StashEvalState.WantToTry), clicked)
    }

    @Test
    fun `selectedState로_지정한_칸만_선택_시맨틱을_갖는다`() {
        composeTestRule.setStashContent {
            StashEvalStatGrid(
                favoriteCount = 12,
                averageCount = 5,
                avoidCount = 3,
                wantToTryCount = 8,
                selectedState = StashEvalState.Average,
                onStateClick = {},
            )
        }

        composeTestRule.onNodeWithText(labelOf(StashEvalState.Average))
            .assertIsSelected()
            // 필터는 한 번에 하나 — selectedState 가 nullable 단일 값이다
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.RadioButton))
        StashEvalState.entries
            .filterNot { it == StashEvalState.Average }
            .forEach { composeTestRule.onNodeWithText(labelOf(it)).assertIsNotSelected() }
    }

    @Test
    fun `onStateClick이_없으면_표시_전용이라_칸이_클릭되지_않는다`() {
        composeTestRule.setStashContent {
            StashEvalStatGrid(
                favoriteCount = 12,
                averageCount = 5,
                avoidCount = 3,
                wantToTryCount = 8,
            )
        }

        StashEvalState.entries.forEach { state ->
            composeTestRule.onNodeWithText(labelOf(state)).assertHasNoClickAction()
        }
    }
}

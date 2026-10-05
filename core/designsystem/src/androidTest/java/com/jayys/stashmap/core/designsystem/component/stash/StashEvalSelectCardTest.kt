package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jayys.stashmap.core.designsystem.testing.labelOf
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * [StashEvalSelectCard] / [StashEvalSelectGrid] 계약 테스트
 *
 * 색만 바뀌고 시맨틱이 없으면 TalkBack 사용자는 뭘 골랐는지 알 수 없다 —
 * 선택 상태가 `Modifier.selectable` 로 실제 노출되는지를 못 박는다
 */
class StashEvalSelectCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `카드는_라디오버튼_role로_선택_상태를_노출한다`() {
        composeTestRule.setStashContent {
            StashEvalSelectCard(
                state = StashEvalState.Favorite,
                selected = true,
                onClick = {},
            )
        }

        composeTestRule.onNodeWithText(labelOf(StashEvalState.Favorite))
            .assertIsSelected()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.RadioButton))
    }

    @Test
    fun `선택되지_않은_카드는_미선택_시맨틱을_갖는다`() {
        composeTestRule.setStashContent {
            StashEvalSelectCard(
                state = StashEvalState.Favorite,
                selected = false,
                onClick = {},
            )
        }

        composeTestRule.onNodeWithText(labelOf(StashEvalState.Favorite)).assertIsNotSelected()
    }

    @Test
    fun `카드를_누르면_선택_콜백이_온다`() {
        var clicks = 0

        composeTestRule.setStashContent {
            StashEvalSelectCard(
                state = StashEvalState.Avoid,
                selected = false,
                onClick = { clicks++ },
            )
        }

        composeTestRule.onNodeWithText(labelOf(StashEvalState.Avoid)).performClick()

        assertEquals(1, clicks)
    }

    @Test
    fun `그리드는_네_상태를_모두_선택_가능한_카드로_그린다`() {
        composeTestRule.setStashContent {
            StashEvalSelectGrid(selected = null, onSelect = {})
        }

        StashEvalState.entries.forEach { state ->
            composeTestRule.onNodeWithText(labelOf(state)).assertIsDisplayed().assertIsNotSelected()
        }
        composeTestRule.onAllNodes(isSelectable()).assertCountEquals(StashEvalState.entries.size)
    }

    @Test
    fun `그리드에서_카드를_누르면_그_상태가_콜백으로_온다`() {
        val selected = mutableListOf<StashEvalState>()

        composeTestRule.setStashContent {
            StashEvalSelectGrid(selected = null, onSelect = { selected += it })
        }

        composeTestRule.onNodeWithText(labelOf(StashEvalState.WantToTry)).performClick()

        assertEquals(listOf(StashEvalState.WantToTry), selected)
    }

    @Test
    fun `그리드는_selected에_해당하는_카드_하나만_선택_상태로_둔다`() {
        composeTestRule.setStashContent {
            StashEvalSelectGrid(selected = StashEvalState.Average, onSelect = {})
        }

        composeTestRule.onNodeWithText(labelOf(StashEvalState.Average)).assertIsSelected()
        StashEvalState.entries
            .filterNot { it == StashEvalState.Average }
            .forEach { composeTestRule.onNodeWithText(labelOf(it)).assertIsNotSelected() }
    }
}

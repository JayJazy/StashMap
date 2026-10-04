package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * [StashSegmentedSelect] 계약 테스트
 *
 * 다중 선택이라 selectable 이 아니라 toggleable(Role.Checkbox) 이다.
 * 라디오처럼 selectable 로 바꿔 끼우면 "여러 개 켬" 이 시맨틱상 표현되지 않으므로 isOn/isOff 로 고정
 */
class StashSegmentedSelectTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val labels = listOf("배달", "포장", "매장")

    // StashIcons 는 @Composable getter(vectorResource) 라 컴포지션 안에서만 읽을 수 있다
    @Composable
    private fun segmentItems() = listOf(
        StashSegmentItem(label = labels[0], icon = StashIcons.Truck),
        StashSegmentItem(label = labels[1], icon = StashIcons.ShoppingBag),
        StashSegmentItem(label = labels[2]),
    )

    @Test
    fun `항목마다_체크박스_role의_토글_노드가_하나씩_생긴다`() {
        composeTestRule.setStashContent {
            StashSegmentedSelect(items = segmentItems(), selectedIndices = emptySet(), onToggle = {})
        }

        composeTestRule.onAllNodes(isToggleable()).assertCountEquals(labels.size)
        labels.forEach { label ->
            composeTestRule.onNodeWithText(label)
                .assertIsDisplayed()
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Checkbox))
        }
    }

    @Test
    fun `selectedIndices에_든_항목만_켜짐으로_읽힌다`() {
        composeTestRule.setStashContent {
            StashSegmentedSelect(items = segmentItems(), selectedIndices = setOf(0, 2), onToggle = {})
        }

        composeTestRule.onNodeWithText("배달").assertIsOn()
        composeTestRule.onNodeWithText("포장").assertIsOff()
        composeTestRule.onNodeWithText("매장").assertIsOn()
    }

    @Test
    fun `항목을_누르면_그_인덱스가_콜백으로_온다`() {
        val toggled = mutableListOf<Int>()

        composeTestRule.setStashContent {
            StashSegmentedSelect(
                items = segmentItems(),
                selectedIndices = emptySet(),
                onToggle = { toggled += it },
            )
        }

        composeTestRule.onNodeWithText("매장").performClick()
        composeTestRule.onNodeWithText("배달").performClick()

        assertEquals(listOf(2, 0), toggled)
    }

    @Test
    fun `여러_항목을_동시에_켤_수_있다`() {
        composeTestRule.setStashContent {
            var selected by remember { mutableStateOf(emptySet<Int>()) }
            StashSegmentedSelect(
                items = segmentItems(),
                selectedIndices = selected,
                onToggle = { index ->
                    selected = if (index in selected) selected - index else selected + index
                },
            )
        }

        composeTestRule.onNodeWithText("배달").performClick()
        composeTestRule.onNodeWithText("포장").performClick()

        composeTestRule.onNodeWithText("배달").assertIsOn()
        composeTestRule.onNodeWithText("포장").assertIsOn()
        composeTestRule.onNodeWithText("매장").assertIsOff()
    }

    @Test
    fun `켜진_항목을_다시_누르면_꺼진다`() {
        composeTestRule.setStashContent {
            var selected by remember { mutableStateOf(setOf(1)) }
            StashSegmentedSelect(
                items = segmentItems(),
                selectedIndices = selected,
                onToggle = { index ->
                    selected = if (index in selected) selected - index else selected + index
                },
            )
        }

        composeTestRule.onNodeWithText("포장").assertIsOn().performClick()

        composeTestRule.onNodeWithText("포장").assertIsOff()
    }
}

package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.jayys.stashmap.core.designsystem.testing.labelOf
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState
import org.junit.Rule
import org.junit.Test

/**
 * [StashDistBar] 계약 테스트
 *
 * 가장 터지기 쉬운 자리: 평가가 하나도 없으면 세 세그먼트가 전부 `weight(0f)` 가 된다.
 * Compose 는 weight 에 0 이하를 넣으면 IllegalArgumentException 을 던지므로
 * 0 인 세그먼트는 아예 빼고 빈 트랙만 그려야 한다
 */
class StashDistBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val tag = "distBar"

    @Test
    fun `평가가_하나도_없으면_크래시_없이_빈_트랙만_그린다`() {
        composeTestRule.setStashContent {
            StashDistBar(
                favoriteCount = 0,
                averageCount = 0,
                avoidCount = 0,
                modifier = Modifier.testTag(tag),
            )
        }

        composeTestRule.onNodeWithTag(tag).assertIsDisplayed()
        // 읽을 비율이 없으니 접근성 설명도 붙이지 않는다
        composeTestRule.onNodeWithTag(tag).assertContentDescriptionEquals()
    }

    @Test
    fun `비율이_있으면_상태별_라벨과_개수를_접근성_설명으로_읽어준다`() {
        composeTestRule.setStashContent {
            StashDistBar(
                favoriteCount = 78,
                averageCount = 15,
                avoidCount = 7,
                modifier = Modifier.testTag(tag),
            )
        }

        composeTestRule.onNodeWithTag(tag).assertContentDescriptionContains(
            "${labelOf(StashEvalState.Favorite)} 78",
            substring = true,
        )
        composeTestRule.onNodeWithTag(tag).assertContentDescriptionContains(
            "${labelOf(StashEvalState.Average)} 15",
            substring = true,
        )
        composeTestRule.onNodeWithTag(tag).assertContentDescriptionContains(
            "${labelOf(StashEvalState.Avoid)} 7",
            substring = true,
        )
    }

    @Test
    fun `개수가_0인_상태는_설명에서_빠진다`() {
        composeTestRule.setStashContent {
            StashDistBar(
                favoriteCount = 5,
                averageCount = 0,
                avoidCount = 0,
                modifier = Modifier.testTag(tag),
            )
        }

        composeTestRule.onNodeWithTag(tag).assertContentDescriptionEquals(
            "${labelOf(StashEvalState.Favorite)} 5",
        )
    }
}

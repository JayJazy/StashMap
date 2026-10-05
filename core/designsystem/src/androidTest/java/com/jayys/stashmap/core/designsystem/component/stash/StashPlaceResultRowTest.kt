package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * [StashPlaceResultRow] 계약 테스트
 *
 * 검색 결과에서 고른 장소는 체크 아이콘으로만 구분되기 쉬운데,
 * selectable 로 선택 상태가 올라와야 스크린리더에서도 뭘 골랐는지 알 수 있다
 */
class StashPlaceResultRowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `이름_주소_카테고리_거리가_모두_보인다`() {
        composeTestRule.setStashContent {
            StashPlaceResultRow(
                name = "성수동 국밥",
                address = "서울 성동구 연무장길 12",
                onClick = {},
                category = "한식",
                distance = "120m",
            )
        }

        composeTestRule.onNodeWithText("성수동 국밥").assertIsDisplayed()
        composeTestRule.onNodeWithText("서울 성동구 연무장길 12").assertIsDisplayed()
        composeTestRule.onNodeWithText("한식").assertIsDisplayed()
        composeTestRule.onNodeWithText("120m").assertIsDisplayed()
    }

    @Test
    fun `선택된_행만_라디오버튼_role의_선택_시맨틱을_갖는다`() {
        composeTestRule.setStashContent {
            Column {
                StashPlaceResultRow(
                    name = "성수동 국밥",
                    address = "서울 성동구 연무장길 12",
                    onClick = {},
                    selected = true,
                )
                StashPlaceResultRow(
                    name = "연남동 파스타",
                    address = "서울 마포구 성미산로 1",
                    onClick = {},
                    selected = false,
                )
            }
        }

        composeTestRule.onNode(hasText("성수동 국밥"))
            .assertIsSelected()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.RadioButton))
        composeTestRule.onNode(hasText("연남동 파스타")).assertIsNotSelected()
    }

    @Test
    fun `행을_누르면_콜백이_온다`() {
        var clicks = 0

        composeTestRule.setStashContent {
            StashPlaceResultRow(
                name = "성수동 국밥",
                address = "서울 성동구 연무장길 12",
                onClick = { clicks++ },
            )
        }

        composeTestRule.onNodeWithText("성수동 국밥").performClick()

        assertEquals(1, clicks)
    }
}

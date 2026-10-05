package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/**
 * 맛집 리스트의 가게 카드 — 썸네일 + 이름·메타·메모 + 평가 칩
 *
 * @param name 가게 이름
 * @param state 평가 상태
 * @param onClick 카드 클릭 콜백
 * @param meta 카테고리·지역·거리를 한 줄로 이은 문자열 (예: "양식 · 성수동 · 1.2km")
 * @param memo 한줄 메모
 * @param thumbnail 썸네일 이미지 (null 이면 플레이스홀더)
 */
@Composable
fun StashRestaurantCard(
    name: String,
    state: StashEvalState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    meta: String? = null,
    memo: String? = null,
    thumbnail: Painter? = null,
) {
    val colors = MaterialTheme.stashColorTokens

    StashCard(modifier = modifier.fillMaxWidth(), onClick = onClick) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(StashSpacing.s3),
            verticalAlignment = Alignment.Top,
        ) {
            StashThumbnail(painter = thumbnail, size = ThumbnailSize)

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(StashSpacing.s1),
            ) {
                StashText(
                    text = name,
                    role = StashTextRole.H3,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                if (meta != null) {
                    StashText(
                        text = meta,
                        role = StashTextRole.BodySm,
                        color = colors.fgMuted,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                if (memo != null) {
                    StashText(
                        text = memo,
                        role = StashTextRole.BodySm,
                        color = colors.fg,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            StashEvalIconChip(state = state, size = StashEvalChipSize.Md)
        }
    }
}

private val ThumbnailSize = 64.dp

@Composable
private fun StashRestaurantCardShowcase() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s3),
    ) {
        StashRestaurantCard(
            name = "마루 비스트로",
            state = StashEvalState.Favorite,
            onClick = {},
            meta = "양식 · 성수동 · 1.2km",
            memo = "여기 파스타 짱. 창가 자리 추천.",
        )
        StashRestaurantCard(
            name = "강남 우동집",
            state = StashEvalState.Average,
            onClick = {},
            meta = "일식 · 역삼동 · 0.4km",
        )
        StashRestaurantCard(
            name = "가보고 싶은 베이커리",
            state = StashEvalState.WantToTry,
            onClick = {},
            meta = "베이커리 · 연남동",
        )
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashRestaurantCardLightPreview() {
    StashTheme(darkTheme = false) {
        StashRestaurantCardShowcase()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashRestaurantCardDarkPreview() {
    StashTheme(darkTheme = true) {
        StashRestaurantCardShowcase()
    }
}

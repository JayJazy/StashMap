package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashMotion
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashShadowLevel
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashShadow

/**
 * Stash Design System 바텀 시트.
 *
 * 전체 화면 [Box] 안에 스크림 오버레이(탭 시 [onDismiss])와 하단 정렬 패널을 직접 구성한다.
 * 패널은 상단 xl 반경, sheet 그림자, 그랩 핸들을 가진다. 단일 [AnimatedVisibility] 로
 * 스크림은 페이드, 패널은 [Modifier.animateEnterExit] 의 slide 로 [StashMotion] 타이밍에 맞춰
 * 등장/퇴장한다. (열릴 때 슬라이드 인 + 페이드 인, 닫힐 때 슬라이드 아웃 + 페이드 아웃)
 *
 * 자체 포함 오버레이 방식이므로 현재 컴포지션 트리 안에 배치해야 한다.
 *
 * @param visible 표시 여부
 * @param onDismiss 스크림 탭 시 호출
 * @param content 시트 콘텐츠
 */
@Composable
fun StashBottomSheet(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = MaterialTheme.stashColorTokens

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(StashMotion.durBase, easing = StashMotion.easeStandard)),
        exit = fadeOut(animationSpec = tween(StashMotion.durBase, easing = StashMotion.easeStandard)),
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.overlay)
                    .clickableNoRipple(onClickLabel = "닫기", onClick = onDismiss),
            )

            // 하단 패널: 아래에서 위로 슬라이드 인, 닫힐 때 아래로 슬라이드 아웃.
            val topShape = RoundedCornerShape(
                topStart = StashRadius.xlDp,
                topEnd = StashRadius.xlDp,
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .animateEnterExit(
                        enter = slideInVertically(
                            animationSpec = tween(StashMotion.durBase, easing = StashMotion.easeStandard),
                            initialOffsetY = { it },
                        ),
                        exit = slideOutVertically(
                            animationSpec = tween(StashMotion.durBase, easing = StashMotion.easeStandard),
                            targetOffsetY = { it },
                        ),
                    )
                    .fillMaxWidth()
                    .stashShadow(level = StashShadowLevel.Sheet, shape = topShape)
                    .clip(topShape)
                    .background(colors.surface)
                    .padding(horizontal = StashSpacing.s4, vertical = StashSpacing.s4),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .padding(bottom = StashSpacing.s3)
                        .size(width = 36.dp, height = 4.dp)
                        .clip(StashRadius.full)
                        .background(colors.borderStrong),
                )
                content()
            }
        }
    }
}

@Composable
private fun StashBottomSheetShowcase() {
    // 전체 화면 오버레이 컴포저블이므로 bg 로 채운 Box 안에 배치한다.
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.stashColorTokens.bg)) {
        StashBottomSheet(visible = true, onDismiss = {}) {
            StashText(text = "바텀 시트 제목", role = StashTextRole.H3)
            StashText(text = "바텀 시트 본문 첫 번째 줄입니다.", role = StashTextRole.BodySm)
            StashText(text = "바텀 시트 본문 두 번째 줄입니다.", role = StashTextRole.BodySm)
        }
    }
}

@Preview(showBackground = true, heightDp = 480)
@Composable
private fun StashBottomSheetLightPreview() {
    StashTheme(darkTheme = false) {
        StashBottomSheetShowcase()
    }
}

@Preview(showBackground = true, heightDp = 480)
@Composable
private fun StashBottomSheetDarkPreview() {
    StashTheme(darkTheme = true) {
        StashBottomSheetShowcase()
    }
}

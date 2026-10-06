package com.jayys.stashmap.core.designsystem.theme.stash

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.model.Evaluation

/**
 * StashMap 의 행동 기반 평가 4상태
 *
 * - 앞 3개는 방문 후 평가, [WantToTry] 는 방문 전 위시리스트
 * - 색·아이콘이 앱 전체에서 같아야 하는 미니 브랜드 시스템 — 화면마다 다시 정하지 말고 [style] 을 쓸 것
 */
enum class StashEvalState(@StringRes val labelRes: Int) {
    Favorite(R.string.favorite),
    Average(R.string.average),
    Avoid(R.string.avoid),
    WantToTry(R.string.want_to_try),
}

/**
 * 한 평가 상태의 표현 묶음
 *
 * @param icon 상태 아이콘
 * @param label 현재 로케일의 상태 라벨
 * @param solid 꽉 찬 배경색. 전경으로 쓰지 말 것 — [subtle] 위에서 라이트 Average 가 2.07:1 로 1.4.11(3:1) 미달.
 *   테두리로 써도 같은 수치라 StatGrid·SelectCard 의 선택 테두리는 아직 미해결
 * @param onSolid [solid] 배경 위에 올리는 색
 * @param subtle 연한 배경색
 * @param subtleFg [subtle] 배경 위에 올리는 전경색 — 텍스트·아이콘 공용
 */
@Immutable
data class StashEvalStyle(
    val icon: ImageVector,
    val label: String,
    val solid: Color,
    val onSolid: Color,
    val subtle: Color,
    val subtleFg: Color,
)

/** 평가 상태의 색·아이콘·라벨 — 시맨틱 토큰(success/warning/error/info)에 1:1 대응 */
val StashEvalState.style: StashEvalStyle
    @Composable
    get() {
        val colors = MaterialTheme.stashColorTokens
        val label = stringResource(labelRes)
        return when (this) {
            StashEvalState.Favorite -> StashEvalStyle(
                icon = StashIcons.Smile,
                label = label,
                solid = colors.success,
                onSolid = colors.successFg,
                subtle = colors.successSubtle,
                subtleFg = colors.successSubtleFg,
            )

            StashEvalState.Average -> StashEvalStyle(
                icon = StashIcons.Meh,
                label = label,
                solid = colors.warning,
                onSolid = colors.warningFg,
                subtle = colors.warningSubtle,
                subtleFg = colors.warningSubtleFg,
            )

            StashEvalState.Avoid -> StashEvalStyle(
                icon = StashIcons.Frown,
                label = label,
                solid = colors.error,
                onSolid = colors.errorFg,
                subtle = colors.errorSubtle,
                subtleFg = colors.errorSubtleFg,
            )

            StashEvalState.WantToTry -> StashEvalStyle(
                icon = StashIcons.Bookmark,
                label = label,
                solid = colors.info,
                onSolid = colors.infoFg,
                subtle = colors.infoSubtle,
                subtleFg = colors.infoSubtleFg,
            )
        }
    }

/** core:model 의 평가 → DS 표현 상태. when 으로 적어 둬야 한쪽에 상태가 늘면 컴파일이 막는다 */
fun Evaluation.toStashEvalState(): StashEvalState = when (this) {
    Evaluation.Favorite -> StashEvalState.Favorite
    Evaluation.Average -> StashEvalState.Average
    Evaluation.Avoid -> StashEvalState.Avoid
    Evaluation.WantToTry -> StashEvalState.WantToTry
}

/** DS 표현 상태 → core:model 의 평가 */
fun StashEvalState.toEvaluation(): Evaluation = when (this) {
    StashEvalState.Favorite -> Evaluation.Favorite
    StashEvalState.Average -> Evaluation.Average
    StashEvalState.Avoid -> Evaluation.Avoid
    StashEvalState.WantToTry -> Evaluation.WantToTry
}

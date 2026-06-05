package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Stash Design System 별점 표시/입력.
 *
 * 5각 별을 [Canvas]/[Path] 로 직접 그린다. 채워진 별은 gold500 외곽선 + gold400 채움,
 * 빈 별은 fgSubtle 외곽선을 사용하며, 소수 점수는 가로 클립으로 부분 채움을 표현한다.
 * [onRatingChange] 가 있으면 별 단위로 탭 입력을 받는다.
 *
 * @param rating 현재 점수 (0f..max)
 * @param max 최대 별 개수
 * @param starSize 별 한 개 크기
 * @param onRatingChange 점수 변경 콜백 (null 이면 읽기 전용)
 */
@Composable
fun StashRating(
    rating: Float,
    modifier: Modifier = Modifier,
    max: Int = 5,
    starSize: Dp = 20.dp,
    onRatingChange: ((Int) -> Unit)? = null,
) {
    val colors = MaterialTheme.stashColorTokens
    val filledOutline = colors.gold500
    val filledFill = colors.gold400
    val emptyOutline = colors.fgSubtle
    val interactive = onRatingChange != null

    Row(
        modifier = modifier.semantics {
            contentDescription = "$max 점 만점에 $rating 점"
        },
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s1),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (index in 0 until max) {
            // 이 별이 채워지는 비율 (0f..1f)
            val fillFraction = (rating - index).coerceIn(0f, 1f)

            val starCanvas = @Composable {
                Canvas(modifier = Modifier.size(starSize)) {
                    val path = starPath(size)

                    // 빈 별 외곽선
                    drawPath(
                        path = path,
                        color = emptyOutline,
                        style = Stroke(width = size.minDimension * 0.06f),
                    )

                    if (fillFraction > 0f) {
                        clipRect(right = size.width * fillFraction) {
                            drawPath(path = path, color = filledFill)
                            drawPath(
                                path = path,
                                color = filledOutline,
                                style = Stroke(width = size.minDimension * 0.06f),
                            )
                        }
                    }
                }
            }

            if (interactive) {
                // 시각 별은 starSize 를 유지하되, 터치 타깃은 최소 48dp 로 확장한다.
                Box(
                    modifier = Modifier
                        .sizeIn(minWidth = MinTouchTarget, minHeight = MinTouchTarget)
                        .clickableNoRipple(
                            role = Role.Button,
                            onClickLabel = "${index + 1} 점",
                        ) { onRatingChange(index + 1) },
                    contentAlignment = Alignment.Center,
                ) {
                    starCanvas()
                }
            } else {
                starCanvas()
            }
        }
    }
}

/** 인터랙티브 별점의 최소 터치 타깃(Material 접근성 권장치). */
private val MinTouchTarget: Dp = 48.dp

/**
 * 주어진 [canvasSize] 에 내접하는 5각 별 [Path] 를 생성한다.
 */
private fun DrawScope.starPath(canvasSize: Size): Path {
    val cx = canvasSize.width / 2f
    val cy = canvasSize.height / 2f
    val outerRadius = min(cx, cy) * 0.95f
    val innerRadius = outerRadius * 0.4f
    val points = 5
    val path = Path()

    // 12시 방향에서 시작하도록 -90도 오프셋
    val startAngle = -PI / 2f
    for (i in 0 until points * 2) {
        val radius = if (i % 2 == 0) outerRadius else innerRadius
        val angle = startAngle + i * PI / points
        val x = cx + radius * cos(angle).toFloat()
        val y = cy + radius * sin(angle).toFloat()
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    return path
}

@Composable
private fun StashRatingShowcase() {
    var rating by remember { mutableStateOf(3) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s4),
    ) {
        StashRating(rating = rating.toFloat(), onRatingChange = { rating = it })
        StashRating(rating = 3.5f)
    }
}

@Preview(showBackground = true)
@Composable
private fun StashRatingLightPreview() {
    StashTheme(darkTheme = false) {
        StashRatingShowcase()
    }
}

@Preview(showBackground = true)
@Composable
private fun StashRatingDarkPreview() {
    StashTheme(darkTheme = true) {
        StashRatingShowcase()
    }
}

package com.jayys.stashmap.core.designsystem.theme.stash

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.pow

/**
 * 시맨틱 토큰 쌍의 WCAG 대비
 *
 * 색은 계측 테스트로 단언할 공개 시맨틱이 없다 → 쌍의 정의만 여기서 막는다.
 * 어느 면에 어떤 토큰을 올렸는지(= 사용처)는 못 잡으니 리뷰로 남는다
 */
class StashColorContrastTest {

    @Test
    fun `subtle 면과 짝 전경은 텍스트 대비를 넘는다`() {
        themes().forEach { (themeName, colors) ->
            subtlePairs(colors).forEach { (pairName, pair) ->
                val (surface, foreground) = pair
                assertAtLeast(TEXT_MIN, surface, foreground, "$themeName $pairName")
            }
        }
    }

    @Test
    fun `solid 면과 짝 전경은 최소 비텍스트 대비를 넘는다`() {
        themes().forEach { (themeName, colors) ->
            solidPairs(colors).forEach { (pairName, pair) ->
                val (surface, foreground) = pair
                assertAtLeast(NON_TEXT_MIN, surface, foreground, "$themeName $pairName")
            }
        }
    }

    /**
     * solid 쌍에 텍스트를 올리려면 4.5 가 필요한데 라이트 success 는 3.30 이라 못 올린다.
     * 현재는 아이콘(onSolid)으로만 쓰여 문제없음 — 통과 목록이 줄어들면 실패시켜 알린다
     */
    @Test
    fun `텍스트를 올릴 수 있는 solid 쌍 목록이 줄지 않았다`() {
        val textCapable = themes().flatMap { (themeName, colors) ->
            solidPairs(colors)
                .filter { (_, pair) -> contrast(pair.first, pair.second) >= TEXT_MIN }
                .map { (pairName, _) -> "$themeName $pairName" }
        }.toSet()

        assertTrue(
            "텍스트 가능 solid 쌍이 줄었다: ${TEXT_CAPABLE_SOLID_PAIRS - textCapable}",
            textCapable.containsAll(TEXT_CAPABLE_SOLID_PAIRS),
        )
    }

    private fun themes() = listOf("라이트" to LightStashColors, "다크" to DarkStashColors)

    private fun subtlePairs(c: StashColors) = listOf(
        "accent" to (c.accentSubtle to c.accentSubtleFg),
        "success" to (c.successSubtle to c.successSubtleFg),
        "warning" to (c.warningSubtle to c.warningSubtleFg),
        "error" to (c.errorSubtle to c.errorSubtleFg),
        "info" to (c.infoSubtle to c.infoSubtleFg),
    )

    private fun solidPairs(c: StashColors) = listOf(
        "accent" to (c.accent to c.accentFg),
        "success" to (c.success to c.successFg),
        "warning" to (c.warning to c.warningFg),
        "error" to (c.error to c.errorFg),
        "info" to (c.info to c.infoFg),
        "chip" to (c.chipBg to c.chipFg),
    )

    private fun assertAtLeast(min: Double, surface: Color, foreground: Color, label: String) {
        val ratio = contrast(surface, foreground)
        assertTrue(
            "$label 대비 ${"%.2f".format(ratio)}:1 < $min:1",
            ratio >= min,
        )
    }

    private companion object {
        const val TEXT_MIN = 4.5
        const val NON_TEXT_MIN = 3.0

        // 라이트 success 는 3.30 이라 빠져 있음
        val TEXT_CAPABLE_SOLID_PAIRS = setOf(
            "라이트 accent", "라이트 warning", "라이트 error", "라이트 info", "라이트 chip",
            "다크 accent", "다크 success", "다크 warning", "다크 error", "다크 info", "다크 chip",
        )
    }
}

/** WCAG 2.x 상대휘도 — lerp 결과는 Oklab 공간이라 sRGB 로 되돌린 뒤 읽어야 한다 */
private fun contrast(a: Color, b: Color): Double {
    val la = a.relativeLuminance()
    val lb = b.relativeLuminance()
    return (maxOf(la, lb) + 0.05) / (minOf(la, lb) + 0.05)
}

private fun Color.relativeLuminance(): Double {
    val srgb = convert(ColorSpaces.Srgb)
    fun channel(v: Float): Double {
        val c = v.toDouble()
        return if (c <= 0.03928) c / 12.92 else ((c + 0.055) / 1.055).pow(2.4)
    }
    return 0.2126 * channel(srgb.red) + 0.7152 * channel(srgb.green) + 0.0722 * channel(srgb.blue)
}

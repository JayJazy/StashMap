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

    /**
     * 컴포넌트가 실제로 올리는 (바탕면, 전경) — 텍스트 자리는 1.4.3(4.5:1)
     *
     * [textUsages]·[nonTextUsages] 는 손으로 적은 목록이라 코드와 자동 동기화되지 않는다 →
     * 사용처를 바꾸고 목록을 안 고치면 못 잡는다. 새 위반을 막는 가드가 아니라, 고친 값을 되돌리면 깨지게 박아 둔 못이다
     */
    @Test
    fun `컴포넌트가 쓰는 텍스트 조합은 텍스트 대비를 넘는다`() {
        themes().forEach { (themeName, colors) ->
            textUsages(colors).forEach { usage ->
                assertAtLeast(TEXT_MIN, usage.surface, usage.foreground, "$themeName ${usage.label}")
            }
        }
    }

    /** 아이콘·테두리 자리는 1.4.11(3:1) — 목록의 한계는 위 텍스트 쪽 설명과 같다 */
    @Test
    fun `컴포넌트가 쓰는 아이콘과 테두리 조합은 비텍스트 대비를 넘는다`() {
        themes().forEach { (themeName, colors) ->
            nonTextUsages(colors).forEach { usage ->
                assertAtLeast(NON_TEXT_MIN, usage.surface, usage.foreground, "$themeName ${usage.label}")
            }
        }
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

    /** 배경을 직접 칠하지 않는 자리(Chip 선택·Ghost·SettingRow)는 부모 면 = surface 기준 */
    private fun textUsages(c: StashColors) = listOf(
        Usage("SegmentedSelect 선택 라벨", c.accentSubtle, c.accentSubtleFg),
        Usage("PlaceResultRow 선택 보조 텍스트", c.accentSubtle, c.accentSubtleFg),
        Usage("Chip 선택 라벨", c.surface, c.accentSubtleFg),
        Usage("Button Ghost 라벨", c.surface, c.accentSubtleFg),
        Usage("Toast Neutral 액션", c.surface, c.accentSubtleFg),
        Usage("TextField placeholder", c.fieldBg, c.fgMuted),
        Usage("TapRow placeholder", c.fieldBg, c.fgMuted),
    )

    // fgMuted 는 surface3 위 라이트 3.97 — 아이콘은 되고 텍스트는 안 되니 여긴 비텍스트만 모은다
    private fun nonTextUsages(c: StashColors) = listOf(
        Usage("BottomNavBar 선택 아이콘", c.accentSubtle, c.accentSubtleFg),
        Usage("PlaceResultRow 선택 체크 아이콘", c.accentSubtle, c.accentSubtleFg),
        Usage("Thumbnail 빈 이미지 아이콘", c.surface3, c.fgMuted),
        Usage("TextField leadingIcon", c.fieldBg, c.fgMuted),
        Usage("TapRow chevron", c.fieldBg, c.fgMuted),
        Usage("SettingRow chevron", c.surface, c.fgMuted),
        Usage("StatCard warning 아이콘", c.surface, c.warning),
        // 컨트롤 테두리 — 1.4.11 의 adjacent color 는 복수형이라 안쪽 면과 바깥 면(= 화면 바탕 bg) 둘 다 본다
        // 포커스 상태는 빠져 있다 — 3dp accentRing 이 accent 테두리 바깥이 아니라 위에 덮여(Modifier.border 는
        //  바깥으로 안 자라고, 먼저 선언된 쪽이 나중에 칠해진다) 실제 인접색이 합성색이라 여기서 못 잰다
        Usage("TextField 테두리 안쪽", c.fieldBg, c.fieldBorder),
        Usage("TextField disabled 테두리 안쪽", c.surface2, c.fieldBorder),
        Usage("TextField 테두리 바깥쪽", c.bg, c.fieldBorder),
        Usage("TextField 에러 테두리 안쪽", c.fieldBg, c.error),
    ) + evalBorderUsages(c)

    /** StatGrid·SelectCard 선택 테두리 — solid 은 짝 subtle 위 라이트 3.07 이라 subtleFg 로 그린다 */
    private fun evalBorderUsages(c: StashColors) = listOf(
        "Favorite" to (c.successSubtle to c.successSubtleFg),
        "Average" to (c.warningSubtle to c.warningSubtleFg),
        "Avoid" to (c.errorSubtle to c.errorSubtleFg),
        "WantToTry" to (c.infoSubtle to c.infoSubtleFg),
    ).map { (state, pair) -> Usage("평가 선택 테두리 $state", pair.first, pair.second) }

    private fun assertAtLeast(min: Double, surface: Color, foreground: Color, label: String) {
        val ratio = contrast(surface, foreground)
        assertTrue(
            "$label 대비 ${"%.2f".format(ratio)}:1 < $min:1",
            ratio >= min,
        )
    }

    /** 한 컴포넌트가 한 자리에 올리는 (바탕면, 전경) */
    private data class Usage(val label: String, val surface: Color, val foreground: Color)

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

/** WCAG 2.x 상대휘도 — 다크 *Subtle 은 Oklab 에서 보간되지만 lerp 반환은 sRGB. 토큰이 바뀌어도 안전하게 명시 변환 */
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

package com.jayys.stashmap.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalStashMapColors = staticCompositionLocalOf { LightColors }

/**
 * StashMap 앱의 전체 색상 팔레트
 * 각 색상별로 4단계(Light1, Light2, Dark1, Dark2)로 구성된 선명한 색상 시스템
 */
@Immutable
data class StashMapColors(
    // Grayscale (회색조) - 4단계
    val grayLight1: Color,
    val grayLight2: Color,
    val grayDark1: Color,
    val grayDark2: Color,

    // Purple (브랜드 보라색) - 4단계
    val purpleLight1: Color,
    val purpleLight2: Color,
    val purpleDark1: Color,
    val purpleDark2: Color,

    // Pink (브랜드 핑크색) - 4단계
    val pinkLight1: Color,
    val pinkLight2: Color,
    val pinkDark1: Color,
    val pinkDark2: Color,

    // Red (에러/위험) - 4단계
    val redLight1: Color,
    val redLight2: Color,
    val redDark1: Color,
    val redDark2: Color,

    // Orange (경고) - 4단계
    val orangeLight1: Color,
    val orangeLight2: Color,
    val orangeDark1: Color,
    val orangeDark2: Color,

    // Yellow (주의) - 4단계
    val yellowLight1: Color,
    val yellowLight2: Color,
    val yellowDark1: Color,
    val yellowDark2: Color,

    // Green (성공) - 4단계
    val greenLight1: Color,
    val greenLight2: Color,
    val greenDark1: Color,
    val greenDark2: Color,

    // Blue (정보) - 4단계
    val blueLight1: Color,
    val blueLight2: Color,
    val blueDark1: Color,
    val blueDark2: Color,

    // Teal (액센트) - 4단계
    val tealLight1: Color,
    val tealLight2: Color,
    val tealDark1: Color,
    val tealDark2: Color,

    // Common Colors
    val bgColor: Color,
    val baseColor: Color,
)
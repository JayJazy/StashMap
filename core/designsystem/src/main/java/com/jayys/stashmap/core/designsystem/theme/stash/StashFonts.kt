package com.jayys.stashmap.core.designsystem.theme.stash

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.jayys.stashmap.core.designsystem.R

/**
 * Stash Design System 의 기본 폰트 패밀리.
 *
 * Pretendard Regular(400) / Bold(700) 두 굵기만 제공한다(faux-bold 금지).
 * stash 네임스페이스 내부에서 자기완결적으로 정의해 legacy theme 에 의존하지 않는다.
 */
internal val StashPretendard = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
)

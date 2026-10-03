package com.jayys.stashmap.core.designsystem.modifier

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

/**
 * ripple(indication) 없이 클릭만 처리하는 공통 Modifier
 *
 * press 상태를 직접 관찰해야 하는 컴포넌트는 자체 `interactionSource` 를 가진 [clickable] 을 써야 함
 *
 * @param enabled 클릭 활성화 여부
 * @param role 접근성 role (예: [Role.Button], [Role.Tab])
 * @param onClickLabel 접근성 클릭 라벨
 * @param onClick 클릭 콜백
 */
fun Modifier.clickableNoRipple(
    enabled: Boolean = true,
    role: Role? = null,
    onClickLabel: String? = null,
    onClick: () -> Unit,
): Modifier = clickable(
    interactionSource = null,
    indication = null,
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role,
    onClick = onClick,
)

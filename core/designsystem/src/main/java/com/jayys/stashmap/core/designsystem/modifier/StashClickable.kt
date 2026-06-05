package com.jayys.stashmap.core.designsystem.modifier

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

/**
 * ripple(indication) 없이 클릭만 처리하는 공통 Modifier.
 *
 * 앱 전역에서 일관되게 "ripple 없는 클릭"을 쓰기 위한 단일 진입점이다.
 * 모든 모듈이 의존하는 최하위 모듈(core/designsystem)에 두어 디자인 시스템 컴포넌트와
 * feature 화면이 동일한 구현을 공유한다.
 *
 * `interactionSource = null` 을 넘기면 [clickable] 이 내부에서 필요 시 lazy 로 생성하므로
 * 구식 `composed {}` / `remember { MutableInteractionSource() }` 없이도 동작한다.
 *
 * 단, press 상태를 직접 관찰해야 하는 컴포넌트(예: press 시 배경색을 바꾸는 버튼)는
 * 자체 `interactionSource` 를 가진 [clickable] 을 그대로 써야 한다.
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

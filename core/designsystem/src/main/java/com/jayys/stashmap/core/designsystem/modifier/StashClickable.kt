package com.jayys.stashmap.core.designsystem.modifier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
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

/**
 * 여럿 중 하나를 고르는 자리용 — ripple 없는 [selectable]
 *
 * `clickable` + `semantics { selected = ... }` 를 직접 조합하면 enabled 전파와 선택 상태가
 * 호출마다 제각각이 되므로, 선택 상태가 있는 클릭은 전부 이쪽을 쓸 것
 *
 * @param selected 선택 여부
 * @param enabled 클릭 활성화 여부
 * @param role 접근성 role (예: [Role.RadioButton], [Role.Tab])
 * @param onClick 선택 콜백
 */
fun Modifier.selectableNoRipple(
    selected: Boolean,
    enabled: Boolean = true,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier = selectable(
    selected = selected,
    interactionSource = null,
    indication = null,
    enabled = enabled,
    role = role,
    onClick = onClick,
)

/**
 * 켜고 끄는 자리용 — ripple 없는 [toggleable]
 *
 * indication 을 넘기지 않는 [toggleable] 오버로드는 `LocalIndication` 기본값(검은 반투명 오버레이)을
 * 끌어다 쓰므로, DS 안에서는 반드시 이쪽을 쓸 것
 *
 * @param value 켜짐 여부
 * @param enabled 토글 활성화 여부
 * @param role 접근성 role (예: [Role.Checkbox], [Role.Switch])
 * @param onValueChange 토글 콜백
 */
fun Modifier.toggleableNoRipple(
    value: Boolean,
    enabled: Boolean = true,
    role: Role? = null,
    onValueChange: (Boolean) -> Unit,
): Modifier = toggleable(
    value = value,
    interactionSource = null,
    indication = null,
    enabled = enabled,
    role = role,
    onValueChange = onValueChange,
)

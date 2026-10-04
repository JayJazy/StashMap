package com.jayys.stashmap.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.jayys.stashmap.core.designsystem.R

/**
 * Stash Design System 아이콘 세트
 *
 * - 실제 그림은 `res/drawable/ic_*.xml` — 24x24 뷰포트 / 2dp 외곽선 / round cap·join 으로 통일
 * - 리소스에 박힌 stroke 색은 의미 없음. 색은 `Icon(tint = ...)` 이 덮어씀
 * - legacy `ico_*` 와 이름을 일부러 분리했다. `ico_home`·`ico_calendar`·`ico_moon` 은
 *   아직 기존 화면들이 쓰고 있어서, 같은 이름으로 덮으면 그 화면 아이콘이 말없이 바뀐다
 */
object StashIcons {

    // 평가 4상태
    val Smile: ImageVector @Composable get() = vector(R.drawable.ic_smile)
    val Meh: ImageVector @Composable get() = vector(R.drawable.ic_meh)
    val Frown: ImageVector @Composable get() = vector(R.drawable.ic_frown)
    val Bookmark: ImageVector @Composable get() = vector(R.drawable.ic_bookmark)

    // 이용 형태
    val Truck: ImageVector @Composable get() = vector(R.drawable.ic_truck)
    val ShoppingBag: ImageVector @Composable get() = vector(R.drawable.ic_shopping_bag)
    val Utensils: ImageVector @Composable get() = vector(R.drawable.ic_utensils)

    // 장소·기록
    val MapPin: ImageVector @Composable get() = vector(R.drawable.ic_map_pin)
    val Calendar: ImageVector @Composable get() = vector(R.drawable.ic_calendar)
    val Photo: ImageVector @Composable get() = vector(R.drawable.ic_photo)

    // 액션·내비
    val Plus: ImageVector @Composable get() = vector(R.drawable.ic_plus)
    val Check: ImageVector @Composable get() = vector(R.drawable.ic_check)
    val ChevronRight: ImageVector @Composable get() = vector(R.drawable.ic_chevron_right)
    val Trash: ImageVector @Composable get() = vector(R.drawable.ic_trash)
    val Home: ImageVector @Composable get() = vector(R.drawable.ic_home)
    val User: ImageVector @Composable get() = vector(R.drawable.ic_user)

    // 설정
    val Languages: ImageVector @Composable get() = vector(R.drawable.ic_languages)
    val Moon: ImageVector @Composable get() = vector(R.drawable.ic_moon)
}

@Composable
private fun vector(@DrawableRes id: Int): ImageVector = ImageVector.vectorResource(id)

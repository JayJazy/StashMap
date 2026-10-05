package com.jayys.stashmap.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import com.jayys.stashmap.core.designsystem.R
import java.util.concurrent.ConcurrentHashMap

/**
 * Stash Design System 아이콘 세트
 *
 * - 디자인 시안이 쓴 Lucide(lucide.dev, ISC) path 를 그대로 옮긴 `res/drawable/ic_*.xml`
 * - 24x24 뷰포트 / 2dp 외곽선 / round cap·join. [Bookmark] 만 채움꼴
 * - 리소스에 박힌 색은 의미 없음. 색은 `Icon(tint = ...)` 이 덮어씀 — `Image` 로 쓰면 검정 그대로 나온다
 * - 파싱 결과를 프로세스 단위로 캐싱한다 ([vector] 주석 참고)
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
    val Clock: ImageVector @Composable get() = vector(R.drawable.ic_clock)
    val Photo: ImageVector @Composable get() = vector(R.drawable.ic_photo)
    val Camera: ImageVector @Composable get() = vector(R.drawable.ic_camera)

    // 액션·내비
    val Plus: ImageVector @Composable get() = vector(R.drawable.ic_plus)
    val Check: ImageVector @Composable get() = vector(R.drawable.ic_check)
    val Close: ImageVector @Composable get() = vector(R.drawable.ic_close)
    val ChevronRight: ImageVector @Composable get() = vector(R.drawable.ic_chevron_right)
    val ChevronLeft: ImageVector @Composable get() = vector(R.drawable.ic_chevron_left)
    val ArrowRight: ImageVector @Composable get() = vector(R.drawable.ic_arrow_right)
    val Search: ImageVector @Composable get() = vector(R.drawable.ic_search)
    val Filter: ImageVector @Composable get() = vector(R.drawable.ic_filter)
    val Pencil: ImageVector @Composable get() = vector(R.drawable.ic_pencil)
    val Trash: ImageVector @Composable get() = vector(R.drawable.ic_trash)
    val More: ImageVector @Composable get() = vector(R.drawable.ic_more)
    val Home: ImageVector @Composable get() = vector(R.drawable.ic_home)
    val User: ImageVector @Composable get() = vector(R.drawable.ic_user)

    // 설정
    val Languages: ImageVector @Composable get() = vector(R.drawable.ic_languages)
    val Moon: ImageVector @Composable get() = vector(R.drawable.ic_moon)
    val Sun: ImageVector @Composable get() = vector(R.drawable.ic_sun)
    val Info: ImageVector @Composable get() = vector(R.drawable.ic_info)
    val HelpCircle: ImageVector @Composable get() = vector(R.drawable.ic_help_circle)

    // 음식 카테고리 — PRD 12분류에 매핑해 쓸 것
    val Soup: ImageVector @Composable get() = vector(R.drawable.ic_soup)
    val UtensilsCrossed: ImageVector @Composable get() = vector(R.drawable.ic_utensils_crossed)
    val Fish: ImageVector @Composable get() = vector(R.drawable.ic_fish)
    val Shell: ImageVector @Composable get() = vector(R.drawable.ic_shell)
    val Coffee: ImageVector @Composable get() = vector(R.drawable.ic_coffee)
    val Croissant: ImageVector @Composable get() = vector(R.drawable.ic_croissant)
    val Sandwich: ImageVector @Composable get() = vector(R.drawable.ic_sandwich)
    val Beef: ImageVector @Composable get() = vector(R.drawable.ic_beef)
    val Wine: ImageVector @Composable get() = vector(R.drawable.ic_wine)
    val Salad: ImageVector @Composable get() = vector(R.drawable.ic_salad)
}

/**
 * 드로어블 하나를 [ImageVector] 로 읽어 프로세스 단위로 캐싱
 *
 * `@Composable` 인 `ImageVector.vectorResource(id)` 는 호출 지점 슬롯에만 remember 해서,
 * 같은 아이콘이라도 리스트 아이템마다 XML 을 새로 파싱한다. (전역 캐시를 타는 건 `painterResource` 쪽)
 * 아이콘이 전부 qualifier 없는 `drawable/` 한 벌이라 구성·테마에 따라 달라지지 않으므로 resId 만 키로 쓴다
 */
@Composable
private fun vector(@DrawableRes id: Int): ImageVector {
    val context = LocalContext.current
    return vectorCache.getOrPut(id) {
        ImageVector.vectorResource(context.theme, context.resources, id)
    }
}

private val vectorCache = ConcurrentHashMap<Int, ImageVector>()

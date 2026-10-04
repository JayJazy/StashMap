package com.jayys.stashmap.core.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp

/**
 * Stash Design System 아이콘 세트
 *
 * - 24x24 viewport / 2dp 외곽선 / round cap·join 으로 통일
 * - 채움 색은 [Color.Black] 고정 — `Icon(tint = ...)` 의 ColorFilter 가 덮어씀
 * - 첫 접근 시점까지 [ImageVector] 생성을 미루려고 lazy
 */
object StashIcons {

    // 평가 4상태
    val Smile: ImageVector by lazy { strokeIcon("Smile", FaceCircle, SmileMouth, LeftEye, RightEye) }
    val Meh: ImageVector by lazy { strokeIcon("Meh", FaceCircle, MehMouth, LeftEye, RightEye) }
    val Frown: ImageVector by lazy { strokeIcon("Frown", FaceCircle, FrownMouth, LeftEye, RightEye) }
    val Bookmark: ImageVector by lazy {
        filledIcon("Bookmark", "M19 21l-7-4-7 4V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2v16z")
    }

    // 이용 형태
    val Truck: ImageVector by lazy {
        strokeIcon(
            "Truck",
            "M14 18V6c0-1.1-0.9-2-2-2H4c-1.1 0-2 0.9-2 2v11c0 0.55 0.45 1 1 1h2",
            "M15 18H9",
            "M19 18h2c0.55 0 1-0.45 1-1v-3.65c0-0.23-0.08-0.45-0.22-0.62l-3.48-4.35C18.1 8.14 17.82 8 17.52 8H14",
            "M5 18a2 2 0 1 0 4 0a2 2 0 1 0-4 0",
            "M15 18a2 2 0 1 0 4 0a2 2 0 1 0-4 0",
        )
    }
    val ShoppingBag: ImageVector by lazy {
        strokeIcon(
            "ShoppingBag",
            "M6 2L3 6v14c0 1.1 0.9 2 2 2h14c1.1 0 2-0.9 2-2V6l-3-4z",
            "M3 6h18",
            "M16 10c0 2.21-1.79 4-4 4s-4-1.79-4-4",
        )
    }
    val Utensils: ImageVector by lazy {
        strokeIcon(
            "Utensils",
            "M3 2v7c0 1.1 0.9 2 2 2h4c1.1 0 2-0.9 2-2V2",
            "M7 2v20",
            "M21 15V2c-2.76 0-5 2.24-5 5v6c0 1.1 0.9 2 2 2h3z",
            "M21 15v7",
        )
    }

    // 장소·기록
    val MapPin: ImageVector by lazy {
        strokeIcon(
            "MapPin",
            "M20 10c0 6-8 12-8 12s-8-6-8-12a8 8 0 0 1 16 0z",
            "M9 10a3 3 0 1 0 6 0a3 3 0 1 0-6 0",
        )
    }
    val Calendar: ImageVector by lazy {
        strokeIcon(
            "Calendar",
            "M5 4h14a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2z",
            "M16 2v4",
            "M8 2v4",
            "M3 10h18",
        )
    }
    val Photo: ImageVector by lazy {
        strokeIcon(
            "Photo",
            "M5 3h14a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2z",
            "M7 9a2 2 0 1 0 4 0a2 2 0 1 0-4 0",
            "M21 15l-3.09-3.09a2 2 0 0 0-2.82 0L6 21",
        )
    }

    // 액션·내비
    val Plus: ImageVector by lazy { strokeIcon("Plus", "M5 12h14", "M12 5v14") }
    val Check: ImageVector by lazy { strokeIcon("Check", "M20 6L9 17l-5-5") }
    val ChevronRight: ImageVector by lazy { strokeIcon("ChevronRight", "M9 18l6-6-6-6") }
    val Trash: ImageVector by lazy {
        strokeIcon(
            "Trash",
            "M3 6h18",
            "M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6",
            "M8 6V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2",
            "M10 11v6",
            "M14 11v6",
        )
    }
    val Home: ImageVector by lazy {
        strokeIcon("Home", "M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z", "M9 22V12h6v10")
    }
    val User: ImageVector by lazy {
        strokeIcon(
            "User",
            "M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2",
            "M8 7a4 4 0 1 0 8 0a4 4 0 1 0-8 0",
        )
    }

    // 설정
    val Languages: ImageVector by lazy {
        strokeIcon(
            "Languages",
            "M5 8l6 6",
            "M4 14l6-6 2-3",
            "M2 5h12",
            "M7 2h1",
            "M22 22l-5-10-5 10",
            "M14 18h6",
        )
    }
    val Moon: ImageVector by lazy { strokeIcon("Moon", "M12 3a6 6 0 0 0 9 9 9 9 0 1 1-9-9z") }
}

// 얼굴 3종이 공유하는 조각 — 원·눈은 같고 입만 다름
private const val FaceCircle = "M2 12a10 10 0 1 0 20 0a10 10 0 1 0-20 0"
private const val LeftEye = "M9 9L9.01 9"
private const val RightEye = "M15 9L15.01 9"
private const val SmileMouth = "M8 14c0 0 1.5 2 4 2s4-2 4-2"
private const val MehMouth = "M8 15h8"
private const val FrownMouth = "M16 16c0 0-1.5-2-4-2s-4 2-4 2"

private const val ViewportSize = 24f
private const val StrokeWidth = 2f

private fun strokeIcon(name: String, vararg paths: String): ImageVector =
    iconBuilder(name).apply {
        paths.forEach { path ->
            addPath(
                pathData = addPathNodes(path),
                stroke = SolidColor(Color.Black),
                strokeLineWidth = StrokeWidth,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
            )
        }
    }.build()

private fun filledIcon(name: String, vararg paths: String): ImageVector =
    iconBuilder(name).apply {
        paths.forEach { path ->
            addPath(pathData = addPathNodes(path), fill = SolidColor(Color.Black))
        }
    }.build()

private fun iconBuilder(name: String) = ImageVector.Builder(
    name = name,
    defaultWidth = ViewportSize.dp,
    defaultHeight = ViewportSize.dp,
    viewportWidth = ViewportSize,
    viewportHeight = ViewportSize,
)

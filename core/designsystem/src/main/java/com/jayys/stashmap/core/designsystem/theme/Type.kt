package com.jayys.stashmap.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jayys.stashmap.core.designsystem.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal)
)

enum class TextStyleEnum {
    Title1, Title2, Title3, Title4,
    Body1, Body2, Body3,
    TextButton, TextButton2,
    HeadLine0,
    HeadLine1,
    HeadLine2,
    HeadLine3,
    Caption,
    Caption2,
    Bold3,
    Normal2,
    Button,
    OverLine,
    OverLine2
}

val Int.nonScaledSp
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp

@Composable
fun typography(textStyle: TextStyleEnum): TextStyle {
    val baseColor = MaterialTheme.stashColors.baseColor

    return when (textStyle) {
        TextStyleEnum.Title1 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 20.nonScaledSp,
            lineHeight = 27.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.Title2 -> {
            TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 17.nonScaledSp,
                lineHeight = 23.nonScaledSp,
                color = baseColor,
            )
        }

        TextStyleEnum.Title3 -> {
            TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 15.nonScaledSp,
                lineHeight = 20.nonScaledSp,
                color = baseColor,
            )
        }

        TextStyleEnum.Title4 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 12.nonScaledSp,
            lineHeight = 16.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.Body1 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 17.nonScaledSp,
            lineHeight = 23.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.Body2 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 15.nonScaledSp,
            lineHeight = 22.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.Body3 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 12.nonScaledSp,
            lineHeight = 16.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.TextButton -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 13.nonScaledSp,
            lineHeight = 18.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.TextButton2 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 13.nonScaledSp,
            lineHeight = 17.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.HeadLine0 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 40.nonScaledSp,
            lineHeight = 50.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.HeadLine1 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 34.nonScaledSp,
            lineHeight = 46.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.HeadLine2 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 26.nonScaledSp,
            lineHeight = 38.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.HeadLine3 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 22.nonScaledSp,
            lineHeight = 30.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.Caption -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 12.nonScaledSp,
            lineHeight = 16.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.Caption2 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 12.nonScaledSp,
            lineHeight = 19.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.Bold3 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 12.nonScaledSp,
            lineHeight = 16.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.Normal2 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 17.nonScaledSp,
            lineHeight = 22.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.Button -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 16.nonScaledSp,
            lineHeight = 22.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.OverLine -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 10.nonScaledSp,
            lineHeight = 14.nonScaledSp,
            color = baseColor,
        )

        TextStyleEnum.OverLine2 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 10.nonScaledSp,
            lineHeight = 14.nonScaledSp,
            color = baseColor,
        )
    }
}
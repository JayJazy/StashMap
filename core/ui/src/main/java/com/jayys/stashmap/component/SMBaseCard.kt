package com.jayys.stashmap.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.compose.SpacerHeight
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.theme.TextStyleEnum
import com.jayys.stashmap.core.designsystem.theme.typography

@Composable
fun SMBaseCard(
    title: String,
    content: String,
    icon: Painter,
    iconTint: Color,
    modifier: Modifier = Modifier,
    height: Dp = 78.dp,
    elevation: Dp = 4.dp,
    cardColor: Color = colorResource(id = R.color.bg_color)
) {
    Card(
        modifier = modifier
            .height(height),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = icon,
                    contentDescription = "",
                    tint = iconTint
                )

                Text(
                    text = title,
                    style = typography(TextStyleEnum.Body2)
                )
            }

            SpacerHeight(12.dp)

            Text(
                text = content,
                style = typography(TextStyleEnum.Title2),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSMBaseCard() {
    SMBaseCard(
        title = "Favorites",
        content = "9999",
        icon = painterResource(id = R.drawable.ico_favorite),
        iconTint = colorResource(id = R.color.red3)
    )
}
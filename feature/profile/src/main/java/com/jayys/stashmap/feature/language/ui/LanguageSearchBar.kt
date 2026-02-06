package com.jayys.stashmap.feature.language.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.R

@Composable
fun LanguageSearchBar(
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = {
            Text(
                text = "Search language",
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ico_search),
                contentDescription = "Search",
                tint = colorResource(id = R.color.gray2)
            )
        },
        modifier = modifier
            .background(
                color = colorResource(id = R.color.bg_color),
                shape = RoundedCornerShape(12.dp)
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewLanguageSearchBar() {
    LanguageSearchBar()
}
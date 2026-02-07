package com.jayys.stashmap.feature.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.component.SMTopBar
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.theme.TextStyleEnum
import com.jayys.stashmap.core.designsystem.theme.stashColors
import com.jayys.stashmap.core.designsystem.theme.typography

@Composable
fun ThemeScreen(
    onBack: () -> Unit,
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SMTopBar(
                topBarTitle = stringResource(id = R.string.system_theme),
                onClick = onBack,
            )
        },
        containerColor = MaterialTheme.stashColors.bgColor
    ) { paddingValues ->
        Row(
            modifier = modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 10.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_light_mode),
                        contentDescription = "Light Mode"
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 2.dp,
                        alignment = Alignment.CenterHorizontally
                    )
                ) {
                    RadioButton(
                        selected = !isDarkMode,
                        onClick = { onDarkModeChange(false) },
                        modifier = Modifier.size(30.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.light_mode),
                        style = typography(TextStyleEnum.Body2)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_dark_mode),
                        contentDescription = "Light Mode"
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 2.dp,
                        alignment = Alignment.CenterHorizontally
                    )
                ) {
                    RadioButton(
                        selected = isDarkMode,
                        onClick = { onDarkModeChange(true) },
                        modifier = Modifier.size(30.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.dark_mode),
                        style = typography(TextStyleEnum.Body2)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewThemeScreen() {
    ThemeScreen(
        onBack = {},
        isDarkMode = false,
        onDarkModeChange = {}
    )
}
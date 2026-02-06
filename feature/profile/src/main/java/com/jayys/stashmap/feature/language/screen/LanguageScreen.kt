package com.jayys.stashmap.feature.language.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.component.SMTopBar
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.feature.language.ui.LanguageSearchBar
import com.jayys.stashmap.feature.language.ui.LanguageSelectionItem

@Composable
fun LanguageScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SMTopBar(
                topBarTitle = stringResource(id = R.string.language),
                onClick = onBack,
            )
        },
        containerColor = colorResource(id = R.color.bg_color)
    ) { paddingValues ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = 60.dp
                )
        ) {
            LanguageSearchBar()

            Column {
                LanguageSelectionItem()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLanguageScreen() {
    LanguageScreen(onBack = {})
}
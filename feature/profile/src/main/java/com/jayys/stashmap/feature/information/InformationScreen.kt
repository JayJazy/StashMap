package com.jayys.stashmap.feature.information

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jayys.stashmap.component.SMTopBar
import com.jayys.stashmap.core.designsystem.R

@Composable
fun InformationScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SMTopBar(
                topBarTitle = stringResource(id = R.string.informateion),
                onClick = onBack,
            )
        },
        containerColor = colorResource(id = R.color.bg_color)
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(top = paddingValues.calculateTopPadding())
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewInformationScreen() {
    InformationScreen(onBack = {})
}
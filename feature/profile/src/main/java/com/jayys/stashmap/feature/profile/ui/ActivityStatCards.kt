package com.jayys.stashmap.feature.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.component.SMBaseCard
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.theme.stashColors

@Composable
fun ActivityStatCards(
    topCategory: String,
    addedThisMonth: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SMBaseCard(
                title = stringResource(id = R.string.preferred_category),
                content = topCategory,
                icon = painterResource(id = R.drawable.ico_restaurant),
                iconTint = MaterialTheme.stashColors.orangeLight2,
                height = 78.dp,
                modifier = Modifier.weight(1f)
            )

            SMBaseCard(
                title = stringResource(id = R.string.restaurants_this_month),
                content = addedThisMonth,
                icon = painterResource(id = R.drawable.ico_calendar),
                iconTint = MaterialTheme.stashColors.greenLight2,
                height = 78.dp,
                modifier = Modifier.weight(1f)
            )
        }

        // TODO(영석): 그래프 추가 예정
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewActivityStatCards() {
    ActivityStatCards(
        topCategory = "Korean",
        addedThisMonth = "10",
    )
}
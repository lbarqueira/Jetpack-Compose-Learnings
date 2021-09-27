package com.lbarqueira.composenavigationbasics.ui.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OverviewBody(
    onClickSeeAllAccounts: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .semantics { contentDescription = "Overview Screen" }
    ) {
        TextButton(
            onClick = onClickSeeAllAccounts,
        ) {
            Text(
                text = "SEE ALL ACCOUNTS",
                style = MaterialTheme.typography.button,
            )
        }
    }
}

@Preview(showBackground = true,showSystemUi = true)
@Composable
fun PreviewOverviewBody() {
    OverviewBody{}
}
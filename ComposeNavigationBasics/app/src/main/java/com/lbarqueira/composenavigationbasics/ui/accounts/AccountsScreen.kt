package com.lbarqueira.composenavigationbasics.ui.accounts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text


import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lbarqueira.composenavigationbasics.data.Account
import com.lbarqueira.composenavigationbasics.data.UserData


/**
 * The Accounts screen.
 */
@Composable
fun AccountsBody(
    modifier: Modifier = Modifier,
    accounts: List<Account>,
    onAccountClick: (String) -> Unit = {},
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    )
    {
        Card {
            Column(modifier = Modifier.padding(12.dp)) {
                accounts.forEach { account ->
                    Row(
                        modifier = Modifier
                            .height(80.dp)
                            .clickable {
                                onAccountClick(account.name)

                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically


                    ) {

                        Text(text = account.name)
                        Text(text = account.number.toString())
                        Text(text = account.balance.toString())

                    }
                }
            }
        }
    }
}

/**
 * Detail screen for a single account.
 */
@Composable
fun SingleAccountBody(account: Account) {
    AccountsBody(
        accounts = listOf(account)
    )
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAccountsBody() {
    AccountsBody(
        accounts = UserData.accounts,
        onAccountClick = {}
    )
}
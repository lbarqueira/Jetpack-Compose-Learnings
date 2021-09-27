package com.lbarqueira.composenavigationbasics.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/* Hard-coded data for the Rally sample. */

@Immutable
data class Account(
    val name: String,
    val number: Int,
    val balance: Float,
    val color: Color
)

/**
 * Pretend repository for user's data.
 */
object UserData {
    val accounts: List<Account> = listOf(
        Account(
            "Checking",
            1234,
            2215.13f,
            Color(0xFF004940)
        ),
        Account(
            "Home Savings",
            5678,
            8676.88f,
            Color(0xFF005D57)
        ),
        Account(
            "Car Savings",
            9012,
            987.48f,
            Color(0xFF04B97F)
        ),
        Account(
            "Vacation",
            3456,
            253f,
            Color(0xFF37EFBA)
        ),
        Account(
            "Wine",
            1234,
            2215.13f,
            Color(0xFF004940)
        ),
        Account(
            "Beer",
            5678,
            8676.88f,
            Color(0xFF005D57)
        ),
        Account(
            "Pepsi",
            9012,
            987.48f,
            Color(0xFF04B97F)
        ),
        Account(
            "Seven-up",
            3456,
            253f,
            Color(0xFF37EFBA)
        ),
        Account(
            "Apple",
            9012,
            987.48f,
            Color(0xFF04B97F)
        ),
        Account(
            "Windows",
            3456,
            253f,
            Color(0xFF37EFBA)
        )

    )

    fun getAccount(accountName: String?): Account {
        return accounts.first { it.name == accountName }
    }
}

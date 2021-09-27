package com.lbarqueira.composenavigationbasics

/**
 * Screen metadata for Rally.
 */
enum class RallyScreen {
    Overview,

    Accounts,

    Bills;
    // If the enum class defines any members, separate the constant definitions from the member definitions with a semicolon.

    companion object {
        fun fromRoute(route: String?): RallyScreen =
            when (route?.substringBefore("/")) {
                Accounts.name -> Accounts
                Bills.name -> Bills
                Overview.name -> Overview
                null -> Overview
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
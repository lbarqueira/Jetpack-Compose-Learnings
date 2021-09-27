package com.lbarqueira.composenavigationbasics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.lbarqueira.composenavigationbasics.data.UserData
import com.lbarqueira.composenavigationbasics.ui.accounts.AccountsBody
import com.lbarqueira.composenavigationbasics.ui.accounts.SingleAccountBody
import com.lbarqueira.composenavigationbasics.ui.overview.OverviewBody
import com.lbarqueira.composenavigationbasics.ui.theme.ComposeNavigationBasicsTheme
import java.util.*
import com.lbarqueira.composenavigationbasics.ui.bills.BillsBody

// TAGS
private const val TAG_ALL: String = "AllScreens"


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}

@Composable
fun RallyApp() {
    ComposeNavigationBasicsTheme {

        val allScreens: List<RallyScreen> = RallyScreen.values().toList()
        Log.i(TAG_ALL, "all screen $allScreens")

        // Obtain a NavController by using the rememberNavController() function;
        // this creates and remembers a NavController which survives configuration changes (using rememberSavable).
        // The NavController is associated with a single NavHost composable.
        // The NavHost links the NavController with a navigation graph where composable destinations are specified.

        val navController = rememberNavController()

        val backstackEntry = navController.currentBackStackEntryAsState()

        val currentScreen = RallyScreen.fromRoute(
            backstackEntry.value?.destination?.route
        )


        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = allScreens,
                    onTabSelected = { screen ->
                        navController.navigate(screen.name)
                    },
                    currentScreen = currentScreen
                )
            }
        )
        { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = RallyScreen.Overview.name,
                modifier = Modifier.padding(innerPadding)
            ) {

                val accountsName: String = RallyScreen.Accounts.name
                val billsName: String = RallyScreen.Bills.name

                // definition of navigation destinations
                // routes are represented as strings.
                composable(
                    route = RallyScreen.Overview.name
                ) {
                    OverviewBody(onClickSeeAllAccounts = { navController.navigate(accountsName) })
                }

                composable(RallyScreen.Accounts.name) {
                    AccountsBody(
                        accounts = UserData.accounts, onAccountClick = { name ->
                            navController.navigate("${accountsName}/$name")
                        }
                    )
                }

                composable(RallyScreen.Bills.name) {
                    BillsBody(
                        onclick = { int ->
                            navController.navigate("${billsName}/$int")
                        }
                    )
                }

                //! Navigating with arguments
                //  A named argument is provided inside routes in curly braces like this {argument}.
                //  It is a syntax that looks similar to Kotlin's string template syntax, using the dollar sign $ to escape variable names.

                composable(
                    route = "$accountsName/{name}",
                    arguments = listOf(
                        navArgument("name") {
                            // Make argument type safe
                            type = NavType.StringType
                        }
                    )
                ) { entry -> // Look up "name" in NavBackStackEntry's arguments
                    val accountName = entry.arguments?.getString("name")
                    // Find first name match in UserData
                    val account = UserData.getAccount(accountName)
                    // Pass account to SingleAccountBody
                    SingleAccountBody(account = account)
                }

                composable(
                    route = "$billsName/{integer}",
                    arguments = listOf(
                        navArgument("integer") {
                            // Make argument type safe
                            type = NavType.IntType
                        }
                    )
                ) { entry -> // Look up "integer" in NavBackStackEntry's arguments
                    val integer = entry.arguments?.getInt("integer")
                    // Pass Integer to Text composable
                    Text(text = integer.toString())
                }
            }
        }
    }
}


@Composable
fun RallyTabRow(
    allScreens: List<RallyScreen>,
    onTabSelected: (RallyScreen) -> Unit,
    currentScreen: RallyScreen
) {
    val color = MaterialTheme.colors.onSurface

    Surface(
        Modifier
            .height(56.dp)
            .fillMaxWidth()
    ) {
        Row(Modifier.selectableGroup()) {
            allScreens.forEach { screen ->
                val selected = screen == currentScreen
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(56.dp)
                        .selectable(
                            selected = selected,
                            onClick = {
                                onTabSelected(screen)
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = false,
                                radius = Dp.Unspecified,
                                color = Color.Unspecified
                            )
                        )
                ) {
                    val tabTintColor = if (selected) color else color.copy(alpha = 0.60f)

                    Spacer(Modifier.width(12.dp))
                    Text(screen.name.uppercase(Locale.getDefault()), color = tabTintColor)

                }
            }
        }
    }
}



package com.lbarqueira.composenavigationbasics.ui.bills

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun BillsBody(onclick: (Int) -> Unit) {
    val integer = (0..10).random()

    TextButton(
        onClick = { onclick(integer) }
    ) {
        Text(
            text = "Give me an Integer",
            style = MaterialTheme.typography.button,
        )
    }
}
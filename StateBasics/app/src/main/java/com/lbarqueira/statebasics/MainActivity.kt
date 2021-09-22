package com.lbarqueira.statebasics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lbarqueira.statebasics.ui.theme.StateBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateBasicsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyComposable()
                }
            }
        }
    }
}


@Composable
fun MyComposable() {

    var myValue by remember { mutableStateOf(false) }
    Log.d("Recomposition", "Fist")

    val (text, setText) = remember { mutableStateOf("") }

    val isTextVisible = text.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { myValue = !myValue }
        ) {
            Text(text = "$myValue")
            Log.d("Recomposition", "Second")
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "I am testing state, state is $myValue")

        Spacer(modifier = Modifier.height(4.dp))

        TextFieldInput(text, setText, isTextVisible)

    }

}

// This pattern is called state hoisting. We "hoist" (or lift) state from a composable
// to make it stateless. State hoisting is the main pattern to build unidirectional
// data flow designs in Compose.
// Extracting a stateless composable from a stateful composable makes it easier to reuse the UI
// in different locations.
@Composable
private fun TextFieldInput(
    text: String, // the current value to display
    setText: (String) -> Unit, //  an event that requests the value to change
    isTextVisible: Boolean
) {
    TextField(
        value = text,
        onValueChange = {
            setText(it)
        },
        label = { Text("Label") }
    )
    if (isTextVisible) {
        DisplayText(text)
    } else {
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DisplayText(text: String) {
    Text(text = "I`m just typing: $text")
}


@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    StateBasicsTheme {
        MyComposable()
    }
}
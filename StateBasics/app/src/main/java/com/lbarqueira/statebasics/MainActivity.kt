package com.lbarqueira.statebasics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
    }

}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    StateBasicsTheme {
        MyComposable()
    }
}
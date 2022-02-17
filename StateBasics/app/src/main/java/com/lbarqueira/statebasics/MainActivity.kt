package com.lbarqueira.statebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import com.lbarqueira.statebasics.ui.theme.StateBasicsTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding

/**
 * A ViewModel extracts _state_ from the UI and defines _events_ that can update it.
 * Note: If this ViewModel was also used by the View system, it would be better to continue using LiveData.
 */
class HelloViewModel : ViewModel() {
    // We will explore using mutableStateOf in viewModel and see how it simplifies state code
    // compared to LiveData<String> when targeting Compose.

    // The MutableState class is a single value holder whose reads and writes are observed by Compose.

    // By specifying private set, we're restricting writes to this state object
    // to a private setter only visible inside the ViewModel.

    // state: name
    var name: MutableState<String> = mutableStateOf("")
        private set

    // onNameChanged is an event we're defining that the UI can invoke
    // (events flow up from UI)
    // event: onNameChanged

    fun onNameChanged(newName: String) {
        name.value = newName
    }
}

class MainActivity : ComponentActivity() {

    private val helloViewModel by viewModels<HelloViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            StateBasicsTheme {
                ProvideWindowInsets {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        HelloScreen(modifier = Modifier.statusBarsPadding(), helloViewModel)
                    }
                }
            }
        }
    }
}


// This composable will be a bridge between the state stored in our ViewModel and the
// HelloInput composable.
@Composable
private fun HelloScreen(modifier: Modifier = Modifier, helloViewModel: HelloViewModel) {
    // helloViewModel follows the Lifecycle as the Activity or Fragment that calls this
    // composable function.


    // The event passed to HelloInput use the Kotlin lambda syntax
    // HelloInput(name = name, onNameChange = { helloViewModel.onNameChanged(it) })

    // Alternatively, you can also generate a lambda that calls a single method using the method reference syntax.
    HelloInput(
        modifier = modifier,
        name = helloViewModel.name.value,
        onNameChange = helloViewModel::onNameChanged
    )
}


@Composable
private fun HelloInput(
    modifier: Modifier = Modifier,
    name: String,
    onNameChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextGroup(
            "Top Text",
            modifier = modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
        )
        Box(
            modifier = modifier
                .background(Color.Magenta)
                .fillMaxWidth()
                .weight(1f)
        )
        Text(name)
        TextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Label") }
        )
        Box(
            modifier = modifier
                .background(Color.Magenta)
                .fillMaxWidth()
                .weight(1f)
        )
        TextGroup(
            "Bottom Text",
            modifier = modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun TextGroup(
    text: String,
    style: TextStyle,
    textAlign: TextAlign?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(Color.Yellow)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            modifier = modifier,
            style = style,
            textAlign = textAlign
        )
    }
}
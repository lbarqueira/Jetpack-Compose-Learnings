package com.lbarqueira.statebasics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lbarqueira.statebasics.ui.theme.StateBasicsTheme

/**
 * A ViewModel extracts _state_ from the UI and defines _events_ that can update it.
 */
class HelloViewModel : ViewModel() {

    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    // An observable is any state object that provides a way for anyone to listen for changes to that state.
    // state: name
    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    // onNameChanged is an event we're defining that the UI can invoke
    // (events flow up from UI)
    // event: onNameChanged
    fun onNameChanged(newName: String) {
        _name.value = newName
    }
}

// Changed name from MainActivity to ExampleMainActivity
class ExampleMainActivity : ComponentActivity() {

    private val helloViewModel by viewModels<HelloViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateBasicsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HelloScreen(helloViewModel)
                }
            }
        }
    }
}


// This composable will be a bridge between the state stored in our ViewModel and the
// HelloInput composable.
@Composable
private fun HelloScreen(helloViewModel: HelloViewModel) {
    // helloViewModel follows the Lifecycle as the Activity or Fragment that calls this
    // composable function.

    // name is the _current_ value of [helloViewModel.name]
    // .observeAsState observes a LiveData<T> and converts it into a State<T> object
    // so Compose can react to value changes. It will automatically stop observing when
    // the composable is removed from composition.
    // by is the property delegate syntax in Kotlin, it lets us automatically unwrap the
    // State<T> from observeAsState into a regular T
    val name: String by helloViewModel.name.observeAsState("")

    // The event passed to HelloInput use the Kotlin lambda syntax
    // HelloInput(name = name, onNameChange = { helloViewModel.onNameChanged(it) })

    // Alternatively, you can also generate a lambda that calls a single method using the method reference syntax.
    HelloInput(name = name, onNameChange = helloViewModel::onNameChanged)
}


@Composable
private fun HelloInput(
    name: String,
    onNameChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(name)
        TextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Label") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        MyStatefulComposable()
    }
}

// A stateful composable is a composable that owns a piece of state that it can change over time.
@Composable
fun MyStatefulComposable() {

    var myValue by remember { mutableStateOf(false) }
    Log.d("Recomposition", "Fist")

    val (text, setText) = remember { mutableStateOf("") }

    val isTextVisible = text.isNotBlank()

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
    Column {
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

}

@Composable
private fun DisplayText(text: String) {
    Text(text = "I`m just typing: $text")
}
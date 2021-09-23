package com.lbarqueira.statebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.lbarqueira.statebasics.ui.theme.StateBasicsTheme

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


    // The event passed to HelloInput use the Kotlin lambda syntax
    // HelloInput(name = name, onNameChange = { helloViewModel.onNameChanged(it) })

    // Alternatively, you can also generate a lambda that calls a single method using the method reference syntax.
    HelloInput(
        name = helloViewModel.name.value,
        onNameChange = helloViewModel::onNameChanged
    )
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
    }
}
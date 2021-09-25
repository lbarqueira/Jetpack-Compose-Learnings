package com.luisbarqueira.hiltbasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.luisbarqueira.hiltbasics.ui.theme.HiltBasicsTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var analytics: AnalyticsAdapter
    // analytics instance has been populated by Hilt
    // and it's ready to be used

    @Inject
    lateinit var text: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiltBasicsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(analytics.getString(), text)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, text: String) {
    Column {
        Text(text = "Hello $name!")
        Text(text = "Module: $text")
    }
}


class AnalyticsAdapter @Inject constructor(
    private val service: AnalyticsService,
    private val serviceString: String
) {
    fun getString(): String = "Hi there  ${service.getStringService()} + $serviceString"
}

// AnalyticsAdapter has AnalyticsService as a dependency.
// Therefore, Hilt must also know how to provide instances of AnalyticsService.
// Since AnalyticsService is a class I am constructor-inject it.
class AnalyticsService @Inject constructor() {
    fun getStringService(): String = "Here AnalyticsService"
}

// If AnalyticsService is an interface, then you cannot constructor-inject it.
// Instead, provide Hilt with the binding information by creating an abstract function annotated
// with @Binds inside a Hilt module.

// You also cannot constructor-inject a type that you do not own, such as a class from an
// external library ((classes like Retrofit, OkHttpClient, or Room databases).

// Dependencies that you provide in Hilt modules are available in all generated components
// that are associated with the Android class where you install the Hilt module.




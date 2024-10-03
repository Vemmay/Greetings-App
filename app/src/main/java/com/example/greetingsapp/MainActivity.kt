package com.example.greetingsapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.time.LocalDateTime
import com.example.greetingsapp.ui.theme.GreetingsAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingsAppTheme {
                    Greeting()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greeting() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var greeting by remember { mutableStateOf("") }
    var showPopup by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = text,
            label = { Text(text = "Enter Your Name") },
            onValueChange = {
                text = it
            }
        )
        ElevatedButton(onClick = {
            greeting = greet(text.text)
            showPopup = true // Show the popup when button is clicked
        }) {
            Text("Greet!")
        }
    }
    if (showPopup) {
        ShowPopup(greeting = greeting, showPopup = showPopup) {
            showPopup = false
        }
    }
}

@Composable
fun ShowPopup(greeting: String, showPopup: Boolean, onDismiss: () -> Unit) {
    if (showPopup) {
        Dialog(onDismissRequest = onDismiss) {
            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(greeting)
                    Button(onClick = onDismiss) {
                        Text("X")

                    }
                    }
                }
            }
        }
    }

@RequiresApi(Build.VERSION_CODES.O)
fun greet(name: String): String {
    val time = LocalDateTime.now().hour
    val greeting = when (time) { //gemini auto-complete
        in 0..11 -> "Good Morning"
        in 12..15 -> "Good Afternoon"
        in 16..20 -> "Good Evening"
        else -> "Good Night"
    }
    return "$greeting, $name!"
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GreetingsAppTheme {
        Greeting()
    }
}
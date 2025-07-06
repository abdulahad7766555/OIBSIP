package com.example.unitconverterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unitconverterapp.ui.theme.UnitConverterAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterAppTheme {
                UnitConverterScreen()
            }
        }
    }
}

@Composable
fun UnitConverterScreen() {
    var input by remember { mutableStateOf("") }
    var fromUnit by remember { mutableStateOf("Centimeters") }
    var toUnit by remember { mutableStateOf("Meters") }
    var result by remember { mutableStateOf("") }

    val units = listOf("Centimeters", "Meters", "Grams", "Kilograms")

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Enter value") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        DropdownMenuBox(units, fromUnit) { selected -> fromUnit = selected }
        Spacer(modifier = Modifier.height(10.dp))
        DropdownMenuBox(units, toUnit) { selected -> toUnit = selected }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                result = convertUnits(input, fromUnit, toUnit)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Convert")
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Result: $result")
    }
}

@Composable
fun DropdownMenuBox(options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Text(selectedOption)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

fun convertUnits(valueStr: String, from: String, to: String): String {
    return try {
        val value = valueStr.toDouble()
        val result = when {
            from == to -> value
            from == "Centimeters" && to == "Meters" -> value / 100
            from == "Meters" && to == "Centimeters" -> value * 100
            from == "Grams" && to == "Kilograms" -> value / 1000
            from == "Kilograms" && to == "Grams" -> value * 1000
            else -> 0.0
        }
        result.toString()
    } catch (e: Exception) {
        "Invalid input"
    }
}
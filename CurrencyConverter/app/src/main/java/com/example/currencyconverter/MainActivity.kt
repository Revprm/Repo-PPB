package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CurrencyConverterScreen()
                }
            }
        }
    }
}

@Composable
fun CurrencyConverterScreen() {
    var dollarInput by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val rate = 16803f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Konversi Dolar ke Rupiah",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = dollarInput,
            onValueChange = { input ->
                if (input.matches(Regex("^[0-9]*\\.?[0-9]*$"))) {
                    dollarInput = input
                }
            },
            label = { Text("Masukkan jumlah Dolar") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                focusManager.clearFocus()
                val dollars = dollarInput.toFloatOrNull()
                resultText = if (dollars != null) {
                    val rupiah = dollars * rate
                    "\$${dollars} = Rp ${"%,.2f".format(rupiah)}"
                } else {
                    "Input tidak valid"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Konversi")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (resultText.isNotEmpty()) {
            Text(
                text = resultText,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyConverterScreenPreview() {
    CurrencyConverterTheme {
        CurrencyConverterScreen()
    }
}

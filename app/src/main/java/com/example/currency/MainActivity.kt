package com.example.currency
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sourceCurrencySpinner: Spinner
    private lateinit var destinationCurrencySpinner: Spinner
    private lateinit var convertButton: Button
    private lateinit var resetButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var inputEditText: EditText

    private val currencies = arrayOf("USD", "EUR", "GBP", "JPY")

    private var sourceCurrency = "USD" // Divisa de origen predeterminada
    private var destinationCurrency = "EUR" // Divisa de destino predeterminada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sourceCurrencySpinner = findViewById(R.id.sourceCurrencySpinner)
        destinationCurrencySpinner = findViewById(R.id.destinationCurrencySpinner)
        convertButton = findViewById(R.id.convertButton)
        resetButton = findViewById(R.id.resetButton)
        resultTextView = findViewById(R.id.resultTextView)
        inputEditText = findViewById(R.id.inputEditText)

        // Configurar los Spinners con el adaptador de divisas
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sourceCurrencySpinner.adapter = adapter
        destinationCurrencySpinner.adapter = adapter

        // Establecer selecciones predeterminadas para los Spinners
        sourceCurrencySpinner.setSelection(currencies.indexOf(sourceCurrency))
        destinationCurrencySpinner.setSelection(currencies.indexOf(destinationCurrency))

        sourceCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                sourceCurrency = currencies[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        destinationCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                destinationCurrency = currencies[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        convertButton.setOnClickListener {
            // Obtener el valor ingresado en el campo de entrada
            val inputValue: Double = inputEditText.text.toString().toDoubleOrNull() ?: 0.0

            // Realizar la conversión utilizando los valores de sourceCurrency y destinationCurrency
            val conversionRate = obtenerTasaDeCambio(sourceCurrency, destinationCurrency)
            val convertedValue: Double = inputValue * conversionRate

            // Mostrar el resultado de la conversión en el TextView resultTextView
            resultTextView.text = "Valor de la Conversión: $convertedValue $destinationCurrency"
        }

        resetButton.setOnClickListener {
            // Restablecer los campos, por ejemplo, limpiar resultTextView, inputEditText y seleccionar las monedas predeterminadas
            sourceCurrencySpinner.setSelection(currencies.indexOf("USD"))
            destinationCurrencySpinner.setSelection(currencies.indexOf("EUR"))
            resultTextView.text = "Valor de la Conversión: "
            inputEditText.text.clear()
        }
    }

    private fun obtenerTasaDeCambio(sourceCurrency: String, destinationCurrency: String): Double {
        //Fernando estos son valores inventados por si a caso
        return when (sourceCurrency to destinationCurrency) {
            "USD" to "EUR" -> 0.85
            "USD" to "GBP" -> 0.75
            "USD" to "JPY" -> 110.0
            "EUR" to "USD" -> 1.18
            "EUR" to "GBP" -> 0.89
            "EUR" to "JPY" -> 129.0
            "GBP" to "USD" -> 1.33
            "GBP" to "EUR" -> 1.12
            "GBP" to "JPY" -> 145.0
            "JPY" to "USD" -> 0.0091
            "JPY" to "EUR" -> 0.0078
            "JPY" to "GBP" -> 0.0069
            else -> 1.0 // Si las divisas son las mismas, la tasa de cambio es 1 (sin conversión)
        }
    }
}

package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var bottomResultTextView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.result)
        bottomResultTextView = findViewById(R.id.bottomResult)

        val buttons = listOf(
            R.id.buttonAC, R.id.buttonPercent, R.id.buttonBackspace, R.id.buttonDivide,
            R.id.button7, R.id.button8, R.id.button9, R.id.buttonMultiply,
            R.id.button4, R.id.button5, R.id.button6, R.id.buttonminus,
            R.id.button1, R.id.button2, R.id.button3, R.id.buttonPlus,
            R.id.buttonDoubleZero, R.id.button0, R.id.buttonDot, R.id.buttonEqual
        )

        for (buttonIds in buttons) {
            findViewById<Button>(buttonIds).setOnClickListener { onButtonClick(it as Button) }
        }

        resultTextView.addTextChangedListener {
            updateResult()
        }
    }

    private fun onButtonClick(button: Button) {
        when (button.id) {
            R.id.buttonAC -> {
                resultTextView.text = ""
                findViewById<TextView>(R.id.bottomResult).text = ""
            }
            R.id.buttonBackspace -> {
                val currentText = resultTextView.text.toString()
                if (currentText.isNotEmpty()) {
                    resultTextView.text = currentText.dropLast(1)
                }
            }
            R.id.buttonEqual -> {
                calculateResult()
            }
            R.id.buttonDivide -> {
                appendOperator("/")
            }
            R.id.buttonMultiply -> {
                appendOperator("*")
            }
            R.id.buttonPlus -> {
                appendOperator("+")
            }
            R.id.buttonminus -> {
                appendOperator("-")
            }
            else -> {
                val currentText = resultTextView.text.toString()
                val newText = currentText + button.text
                resultTextView.text = newText
            }
        }
    }

    private fun calculateResult() {
        try {
            val expression = ExpressionBuilder(resultTextView.text.toString()).build()
            val result = expression.evaluate()
            resultTextView.text = result.toString()
        } catch (e: Exception) {
            resultTextView.text = "Error"
        }
    }

    private fun updateResult() {
        try {
            val expression = ExpressionBuilder(resultTextView.text.toString()).build()
            val result = expression.evaluate()
            findViewById<TextView>(R.id.bottomResult).text = " ${result.toString()}"
        } catch (e: Exception) {
            findViewById<TextView>(R.id.bottomResult).text = ""
        }
    }

    private fun appendOperator(operator: String) {
        val currentText = resultTextView.text.toString()
        if (currentText.isNotEmpty() && currentText.last().isDigit()) {
            resultTextView.append(operator)
        } else if (currentText.isNotEmpty() && currentText.last() == '.') {
            val updatedText = currentText.dropLast(1) + operator
            resultTextView.text = updatedText
        }
        findViewById<TextView>(R.id.bottomResult).text = resultTextView.text
    }
}
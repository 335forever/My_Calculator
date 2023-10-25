package ducquan.id.vn.mycalculator

import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var screen: TextView
    private var ketQua: Double = 0.0
    private var bieuThuc: String = ""
    private var display: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screen = findViewById(R.id.screen)

        val button0 = findViewById<Button>(R.id.button0)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)

        val button_cham = findViewById<Button>(R.id.button_cham)

        val buttonPlus = findViewById<Button>(R.id.button_cong)
        val buttonMinus = findViewById<Button>(R.id.button_tru)
        val buttonMultiply = findViewById<Button>(R.id.button_nhan)
        val buttonDivide = findViewById<Button>(R.id.button_chia)
        val buttonEqual = findViewById<Button>(R.id.button_bang)
        val buttonClear = findViewById<Button>(R.id.buttonC)
        val buttonClearEntry = findViewById<Button>(R.id.buttonCE)
        val buttonBackspace = findViewById<Button>(R.id.buttonBS)

        // Sự kiện khi người dùng nhấn nút số
        val numberButtons = listOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9)
        for (button in numberButtons) {
            button.setOnClickListener {
                val number = button.text.toString()
                onNumberButtonClick(number)
            }
        }

        // Sự kiện khi người dùng nhấn nút toán tử
        val operatorButtons = listOf(buttonPlus, buttonMinus, buttonMultiply, buttonDivide)
        for (button in operatorButtons) {
            button.setOnClickListener {
                val newOperator = button.text.toString()
                onOperatorClick(newOperator)
            }
        }

        // Sự kiện khi người dùng nhấn nút "." (dấu chấm)
        button_cham.setOnClickListener {
            onDotClick()
        }

        // Sự kiện khi người dùng nhấn nút "="
        buttonEqual.setOnClickListener {
            onEqualClick()
        }

        // Sự kiện khi người dùng nhấn nút "C" (clear)
        buttonClear.setOnClickListener {
            clear()
        }

        // Sự kiện khi người dùng nhấn nút "CE" (clear entry)
        buttonClearEntry.setOnClickListener {
            clearEntry()
        }

        // Sự kiện khi người dùng nhấn nút "BS" (backspace)
        buttonBackspace.setOnClickListener {
            backSpace()
        }


    }

    private fun onNumberButtonClick(number: String) {
        display += number
        screen.text = display
    }

    private fun onOperatorClick(newOperator: String) {
        bieuThuc += display
        if (!bieuThuc[bieuThuc.length - 1].isDigit())
            bieuThuc = bieuThuc.dropLast(1)
        bieuThuc += newOperator
        display = ""
    }

    private fun onDotClick() {
        display += "."
        screen.text = display
    }

    private fun onEqualClick() {
        bieuThuc += display
        ketQua = evaluateExpression(bieuThuc)
        screen.text = ketQua.toString()
        bieuThuc = ""
        display = ""
    }

    private fun clear() {
        display = ""
        bieuThuc = ""
        screen.text = ""
    }

    private fun clearEntry() {
        display = ""
        screen.text = "0"
    }

    private fun backSpace() {
        if (display.length == 1) {
            display = "0"
            screen.text = display
        }
        else {
            display = display.dropLast(1)
            screen.text = display
        }
    }

    fun evaluateExpression(expression: String): Double {
        // Loại bỏ khoảng trắng (nếu có)
        val cleanExpression = expression.replace("\\s".toRegex(), "")

        val tokens = mutableListOf<String>()
        var currentNumber = ""
        for (char in cleanExpression) {
            if (char.isDigit() || char == '.') {
                currentNumber += char
            } else {
                if (currentNumber.isNotEmpty()) {
                    tokens.add(currentNumber)
                    currentNumber = ""
                }
                tokens.add(char.toString())
            }
        }
        if (currentNumber.isNotEmpty()) {
            tokens.add(currentNumber)
        }

        var result = 0.0
        var currentOperator = "+"

        for (token in tokens) {
            when {
                token == "+" -> currentOperator = "+"
                token == "-" -> currentOperator = "-"
                token == "x" -> currentOperator = "x"
                token == "/" -> currentOperator = "/"
                else -> {
                    val number = token.toDouble()
                    when (currentOperator) {
                        "+" -> result += number
                        "-" -> result -= number
                        "x" -> result *= number
                        "/" -> result /= number
                    }
                }
            }
        }
        return result
    }
}
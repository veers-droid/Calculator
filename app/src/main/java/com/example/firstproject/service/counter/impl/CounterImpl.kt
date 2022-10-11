package com.example.firstproject.service.counter.impl

import android.widget.TextView
import com.example.firstproject.R
import com.example.firstproject.activities.*
import com.example.firstproject.service.counter.Counter
import com.example.firstproject.service.output.OutputController
import com.example.firstproject.service.output.impl.OutputControllerImpl
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.operator.Operator
import kotlin.math.roundToInt


class CounterImpl: Counter
{
    private lateinit var outputController : OutputController
    // we have to implement own factorial operator
    private var factorial: Operator = object : Operator("!", 2, true, PRECEDENCE_POWER + 1)
    {
        override fun apply(vararg args: Double): Double {
            val arg = args[0].toLong()
            require(arg.toDouble() == args[0]) { "Operand for factorial has to be an integer" }
            require(arg >= 0) {
                "The operand of the factorial can not " +
                        "be " +
                        "less than zero"
            }
            var result = 1.0
            for (i in 1..arg) {
                result *= i.toDouble()
            }
            return result
        }
    }

    override fun countPercent(input: TextView, expression: Array<String>)
    {
        outputController = OutputControllerImpl()
        //don't count percent from the operation sign
        //or function
        // or if text is empty, nothing to do
        if (input.text.isEmpty() || outputController.isSign(input.text.last().toString())
            || lastFunction)
        {
            return
        }

        var strNumber = ""

//        go for each symbol till the operation sign
//        appending all letters to a variable
        for (sym: Char in expression[0].reversed() ) {
            if (outputController.isSign(sym.toString()) || sym == '(' || sym == ')') {
                break
            }
            strNumber += sym
            outputController.del(false, input, expression)
        }
        //reverse string so we get our number
        var number = strNumber.reversed().toDouble()
        number /= 100

        var output = number.toString()
        //check if 2 last symbol is .0 -> delete it and then add to our view
        if(output.substring(output.length - 2).contains(".0")
            && output.length <= 3)
        {
            output = output.dropLast(2)
            input.append(output)
            expression[0] += output
            return
        }
        // or add to view received number
        input.append(number.toString())
        expression[0] += output
        lastNumeric = true
        isAnswer = false
    }


    override fun countAnswer(expression: Array<String>, output: TextView) {

        try
        {
            val builtExpression = ExpressionBuilder(expression[0])
                .operator(factorial)
                .build()
            // Calculate the result and display
            val result = (builtExpression// round to the last 5 symbols after dot if need
                .evaluate() * 100000)
                .roundToInt() / 100000.0
            val answer = result.toString()
            if(answer.substring(answer.length - 2) == ".0")//Answer without ".0"
            {
                output.text = answer.substring(0, answer.length - 2)//Answer without ".0"
                expression[0] = output.text.toString()
                isAnswer = true
                lastNumeric = true
            }
            else
            {
                output.text = answer
                expression[0] = output.text.toString()
                isAnswer = true // Result contains a dot
            }
        } catch (ex: ArithmeticException)
        {
            // Display an error message
            output.text = R.string.errorMessage.toString()
            expression[0] = ""
            stateError = true
            lastNumeric = false
        }
    }
}

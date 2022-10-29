package com.example.firstproject.service.output.impl

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.firstproject.activities.*
import com.example.firstproject.service.counter.Counter
import com.example.firstproject.service.counter.impl.CounterImpl
import com.example.firstproject.service.output.OutputController



class OutputControllerImpl: OutputController
{
    private lateinit var counter: Counter

    override fun addNumber(clicked : View, output : TextView, expression: Array<String>)
    {
        val button: Button = clicked as Button
        val num = button.text

        if (output.text.isEmpty())
        {
            output.append(num)
            expression[0] += "$num"
        } else
        {
            if(isAnswer)// change answer to our symbol
            {
                output.text = num
                expression[0] = ""
                expression[0] += "$num"
                isAnswer = false
            }
            else
            {
                output.append(num)
                expression[0] += "$num"
                lastFunction = false
            }
        }
        lastNumeric = true
    }

    override fun addBasicOperationSymbol(clicked: View, output: TextView, expression: Array<String>)
    {
        val button: Button = clicked as Button
        val num = button.text as String
        if(output.text.isEmpty()
            && num != "-")//cant allow to start from arithmetic operation (except unary minus)
        {
            lastNumeric = false
            lastFunction = false
            isAnswer = false
            stateError = false
            parenthesisCounter[0] = 0
            parenthesisCounter[1] = 0
            return
        }

        if (num != "-"
            && output.text.last() == '(')//after open parenthesis its correct to add only unary minus
        {
            return
        }

        //we cannot add 2 arithmetic operations in a row (except for unary minus)
        if (isSign(output.text.toString()) && num != "-")
        {//changes arithmetic symbols
            del(false, output, expression)
            output.append(num)
            expression[0] += num
        } else
        {
            //if we adding unary minus we should add open parenthesis
            if (isSign(output.text.toString()) )
            {
                output.append("(")
                expression[0] += "("
                parenthesisCounter[0] ++
            }
            output.append(num)
            expression[0] += num
        }
        lastNumeric = false
        isAnswer = false
        lastFunction = false
    }

    override fun del(all : Boolean, output: TextView, expression: Array<String>)
    {
        if (all || isAnswer)
        {
            /**
             *  set text field to empty string
             *  changing flags
             */
            output.text = ""
            expression[0] = ""
            lastNumeric = false; stateError = false
            isAnswer = false; lastFunction = false
            parenthesisCounter[0] = 0; parenthesisCounter[1] = 0
            return
        }

        if (output.text.isEmpty())
        {
            lastNumeric = false; stateError = false
            isAnswer = false; lastFunction = false
            parenthesisCounter[0] = 0; parenthesisCounter[1] = 0

            return
        }

        if (lastFunction)
        {
            output.text = output.text.toString().dropLast(1)// delete open parenthesis
            expression[0] = expression[0].dropLast(1)
            when {
                output.text.last() == 'g' -> {
                    output.text = output.text.toString().dropLast(2)// delete ln
                    expression[0] = expression[0].dropLast(3)// delete log
                }
                output.text.last() == '√' -> {
                    output.text = output.text.dropLast(1)//delete √
                    expression[0] = expression[0].dropLast(4)// delete sqrt
                }
                output.text.last() == '0' -> {
                    output.text = output.text.toString().dropLast(3)//delete log
                    expression[0] = expression[0].dropLast(5)// delete log10
                }
                else -> {
                    output.text = output.text.toString().dropLast(3)// delete sin/cos/tan
                    expression[0] = expression[0].dropLast(3)
                }
            }
            lastFunction = false
            parenthesisCounter[0] --
            setFlagsAfterDel(output.text.toString())
            return
        }

        if (output.text.last() == '!')
        {
            output.text = output.text.toString().dropLast(1)// delete open parenthesis
            expression[0] = expression[0].dropLast(4)
            setFlagsAfterDel(output.text.toString())
        }
        output.text = output.text.toString().dropLast(1)// delete open parenthesis
        expression[0] = expression[0].dropLast(1)
        setFlagsAfterDel(output.text.toString())
    }

    override fun addParenthesis(clicked: View, output: TextView, expression: Array<String>)
    {
        val button: Button = clicked as Button
        val parenthesis = button.text as String

        if (parenthesis == "(")
        {
            if (output.text.isNotEmpty() && output.text.last() == '.')
            {
                del(false, output, expression)
            }
            output.append("(")
            lastNumeric
            expression[0] += "("
            parenthesisCounter[0]++
        } else
        {
            if (isAnswer)
            {
                del(true, output, expression)
                return
            }
            if (lastNumeric
                && (parenthesisCounter[1] <= parenthesisCounter[0]))
            {
                output.append(")")
                expression[0] += ")"
                parenthesisCounter[1]++
            }
        }
        isAnswer = false
    }

    override fun addDecimalPoint(output: TextView, expression:Array<String>)
    {
        if (output.text.isEmpty()
            || output.text.last() == '('
            || output.text.last() == ')'
            || output.text.last() == '!')
        {
            return
        }
        if (!hasDot(output.text.toString()) && lastNumeric && !stateError)
        {
            output.append(".")
            expression[0] += "."
            lastNumeric = false
            isAnswer = false
        }
    }

    override fun equalOutput(output: TextView, expression: Array<String>, story:TextView)
    {
        counter = CounterImpl()

        if (lastFunction)//can't evaluate if function without the body
        {
            del(false, output, expression)
        }

        if (expression[0].isEmpty())//if string is empty, nothing to do
        {
            return
        }

        if (isSign(output.text.toString())// also we should remove operation sign and dot from the end
            || output.text.last() == '.')
        {
            del(false, output, expression)
        }

        if (parenthesisCounter[0] > parenthesisCounter[1])// quantity of close parentheses must be equal to open one
        {
            while (parenthesisCounter[0] != parenthesisCounter[1])//if not, add close parenthesis
            {
                output.append(")")
                expression[0] += ")"
                parenthesisCounter[1]++
            }
        }
        if (story.text.isEmpty())//if we have empty story, add our expression here
        {
            story.append(output.text)
        } else
        {
            //in case of clicking on equal button twice, do nothing
            if (getLastNumber(story.text.toString()) != output.text.toString())
            {
                story.append("\n${output.text}")
            }
        }
        // Create an Expression (A class from exp4j library)
        counter.countAnswer(expression, output)
    }

    override fun addPower(output: TextView, expression:Array<String>)
    {
        if (output.text.isEmpty()//cant use power sign after open parenthesis
            || output.text.last() == '(')
        {
            return
        }
        if (isSign(output.text.toString()) || output.text.last() == '.'
            || lastFunction)//change signs
        {
            del(false, output, expression)
        }
        output.append("^")
        lastNumeric = false; isAnswer = false
    }

    override fun addRoot(output: TextView, expression: Array<String>)
    {
        if (output.text.isNotEmpty() && output.text.last() == '.')//remove point and then add root
        {
            del(false, output, expression)
        }

        output.append("√(")
        expression[0] += "sqrt("
        lastFunction = true
        parenthesisCounter[0] ++
        isAnswer = false
    }

    override fun addFunction(clicked: View, output: TextView, expression: Array<String>)
    {
        val button: Button = clicked as Button

        when (val func: String = button.text.toString())
        {
            "lg" -> {
                output.append("$func(")
                expression[0] += "log10("
            }
            "ln" -> {
                output.append("$func(")
                expression[0] += "log("
            }
            else -> {
                output.append("$func(")
                expression[0] += "$func("
            }
        }
        lastFunction = true
        isAnswer = false
        parenthesisCounter[0] ++
    }
}

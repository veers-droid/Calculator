package com.example.firstproject.calculator

import android.view.View
import android.widget.TextView
import com.example.firstproject.calculator.service.counter.impl.CounterImpl
import com.example.firstproject.calculator.service.output.impl.OutputControllerImpl

// Represent whether the lastly pressed key is numeric or not
var lastNumeric: Boolean = false

// Represent that current state is in error or not
var stateError: Boolean = false

//check if we wrote the answer
var isAnswer: Boolean = false

//to handle parenthesis
var parenthesisCounter: Array<Int> = arrayOf(0, 0)

//to better function deleting
var lastFunction: Boolean = false

class Calculator(private var output:TextView, private var storyOutput:TextView): java.io.Serializable {
    var expression: Array<String> = arrayOf("")
    //classes to work with expression and output
    private var outputController = OutputControllerImpl()
    private var counter = CounterImpl()

    fun handleButtonClick(view: View)
    {
        when (view.contentDescription) {
            "number" -> addNumber(view)
            "percent" -> countPercent()
            "operation" -> addOperation(view)
            "function" -> addFunction(view)
            "decimal" -> setDecPoint()
            "factorial" -> factorial()
            "power" -> toPower()
            "sqrt" -> addRoot()
            "parenthesis" -> addParenthesis(view)
            "del" -> del(view)
            "equal" -> isEqual()
            "delAll"-> del(view)
        }
    }

    private fun setDecPoint()
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${output.text}")
        }
        outputController.addDecimalPoint(output, expression)
    }

    private fun countPercent()
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${output.text}")
        }
        counter.countPercent(output, expression)
    }

    private fun isEqual()
    {
        outputController.equalOutput(output, expression, storyOutput)
    }

    private fun factorial()
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${output.text}")
        }
        // we cannot add factorial sign after parenthesis,
        // or arithmetical operation sign
        // and to empty text
        if (output.text.isEmpty()
            || output.text.last() == ')'
            || output.text.last() == '('
            || outputController.isSign(output.text.toString()))
        {
            return
        }
        //delete button if it last
        if (output.text.last() == '.')
        {
            outputController.del(false, output, expression)
        }

        output.append("!")
        // need this type of factorial representation
        // to work properly with our library
        expression[0] += "!(1)"
        TODO("Move to outputController class")
    }

    private fun toPower()
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${output.text}")
        }
        outputController.addPower(output, expression)
    }

    private fun addRoot()
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${output.text}")
        }
        outputController.addRoot(output, expression)
        isAnswer = false
    }

    fun del()
    {
        outputController.del(true, output, expression)
    }

    private fun del(view: View)
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${output.text}")
        }
        if (view.contentDescription != "del")
        {
            outputController.del(true, output, expression)
        } else
        {
            outputController.del(false, output, expression)
        }
    }

    private fun addNumber(view: View)
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${output.text}")
        }
        outputController.addNumber(view, output, expression)
    }

    private fun addOperation(view: View)
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${output.text}")
        }
        outputController.addBasicOperationSymbol(view, output, expression)
    }

    private fun addParenthesis(view: View)
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${output.text}")
        }
        outputController.addParenthesis(view, output, expression)
    }

    private fun addFunction(view: View)
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${output.text}")
        }
        outputController.addFunction(view, output, expression)
        isAnswer = false
    }
}

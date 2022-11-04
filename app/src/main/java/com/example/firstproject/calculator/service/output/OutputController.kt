package com.example.firstproject.calculator.service.output

import android.view.View
import android.widget.TextView
import com.example.firstproject.calculator.isAnswer
import com.example.firstproject.calculator.lastFunction
import com.example.firstproject.calculator.lastNumeric
import com.example.firstproject.calculator.parenthesisCounter

interface OutputController
{
    /**
     * function to add digit symbol to our expression and output
     * @params:
     *  clicked: View - get symbol from clicked button
     *  output: TextView - our expression seen by user
     *  expression: Array<String> - at 0 index our expression to change
     */
    fun addNumber(clicked: View, output: TextView, expression: Array<String>)

    /**
     * function to add arithmetic operation symbol to our expression and output
     * @params:
     *  clicked: View - get symbol from clicked button
     *  output: TextView - our expression seen by user
     *  expression: Array<String> - at 0 index our expression to change
     */
    fun addBasicOperationSymbol(clicked: View, output: TextView, expression: Array<String>)

    /**
     * function to delete all or one symbol from expression
     * @params:
     *  all: Boolean - if true function must delete all expression
     *  output: TextView - our expression seen by user
     *  expression: Array<String> - at 0 index our expression to change
     */
    fun del(all: Boolean, output: TextView, expression: Array<String>)

    /**
     * function to add parenthesis to our expression and output
     * @params:
     *  clicked: View - get parenthesis(open or close) from clicked button
     *  output: TextView - our expression seen by user
     *  expression: Array<String> - at 0 index our expression to change
     */
    fun addParenthesis(clicked: View, output: TextView, expression: Array<String>)

    /**
     * function to add decimal point to our expression and output
     * @params:
     *  output: TextView - our expression seen by user
     *  expression: Array<String> - at 0 index our expression to change
     */
    fun addDecimalPoint(output: TextView, expression: Array<String>)

    /**
     * function to parse our expression and show our answer on our view
     * @params:
     *  output: TextView - our expression seen by user
     *  expression: Array<String> - at 0 index our expression to count
     */
    fun equalOutput(output: TextView, expression: Array<String>, story: TextView)

    /**
     * function to add power symbol to our expression and output
     * @params:
     *  output: TextView - our expression seen by user
     *  expression: Array<String> - at 0 index our expression to change
     */
    fun addPower(output: TextView, expression: Array<String>)

    /**
     * function to add root symbol to our expression and output
     * @params:
     *  output: TextView - our expression seen by user
     *  expression: Array<String> - at 0 index our expression to change
     */
    fun addRoot(output: TextView, expression: Array<String>)

    /**
     * function to add function to our expression and output
     * @params:
     *  clicked: View - get function string representation from clicked button
     *  output: TextView - our expression seen by user
     *  expression: Array<String> - at 0 index our expression to change
     */
    fun addFunction(clicked: View, output: TextView, expression: Array<String>)

    /**
     * function to check is the last symbol is an arithmetical operation sign
     * @params:
     *  num: String - our expression
     * @return:
     *  true - if the last symbol is an binary operator
     *  false - in other case
     */
    fun isSign(num: String): Boolean
    {
        if(num.last() == '+' || num.last() == '-' || num.last() == '*'
            || num.last() == '/' || num.last() == '^')
        {
            return true
        }
        return false
    }

    /**
     * function to check is the last symbol is an arithmetical operation sign
     * @params:
     *  num: String - our expression
     * @return:
     *  true - if our number contains a dot
     *  false - in other case
     */
    fun hasDot(num: String): Boolean
    {
        return getLastNumber(num).contains(".")
    }

    /**
     * function to get last number in its string representation from our expression
     * @param
     *  exp: String - our expression
     * @return
     *  String - the last number
     */
    fun getLastNumber(exp: String): String
    {
        var number = ""
        //reverse our expression so or last number now is first
        for (sym in exp.reversed())
        {
            if (isSign(sym.toString())//number ends
                || sym == '('
                || sym == ')'
                || sym == '\n')
            {
                break
            }
            number += sym
        }
        return number.reversed()//return original number
    }

    /**
     * function to reset flags after deleting operation
     * @param
     *  exp: String - our expression
     */
    fun setFlagsAfterDel(exp: String)
    {
        if (exp.isEmpty())//flags for empty expression
        {
            lastNumeric = false
            lastFunction = false
            isAnswer = false
            parenthesisCounter[0] = 0
            parenthesisCounter[1] = 0
            return
        }
        val lastSymbol = exp.last()//getting the last symbol from our expression
        if (lastSymbol.isDigit())//flags for digit symbol
        {
            lastNumeric = true
            lastFunction = false
            isAnswer = false
            return
        }
        if (isSign(exp))//flags for arithmetical sign symbol
        {
            lastNumeric = false
            lastFunction = false
            isAnswer = false
            return
        }
        if (lastSymbol == '.')//flags for dot symbol
        {
            lastNumeric = false
            lastFunction = false
            isAnswer = false
            return
        }
        if (lastSymbol == ')')// flags for close parenthesis
        {
            lastNumeric = true
            lastFunction = false
            isAnswer = false
            return
        }
        if (lastSymbol == '(')//if last symbol is open parenthesis
        //check the symbol before the last one
        {
            // if that symbol is letter that means that we need flags for function
            if (exp.dropLast(1).last().isLetter())
            {
                lastFunction = true
                lastNumeric = true
                isAnswer = false
                return
            } else//if not flags for a simple open parenthesis
            {
                lastFunction = false
                lastNumeric = true
                isAnswer = false
                return
            }
        }
    }
}

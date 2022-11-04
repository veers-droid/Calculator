package com.example.firstproject.calculator.service.counter

import android.widget.TextView

interface Counter {
    /**
     * function to count percent of a number (equals to divide it 100 times
     * @param:
     *  input:TextView - our text
     *  expression: Array<String> - our expression
     */
    fun countPercent(input:TextView, expression:Array<String>)

    /**
     * function to count answer from parsed expression given from OutputController class
     * @param:
     *  expression: Array<String> - our expression from which we create a class
     *  from exp4j library and gets the result
     *  @param
     *      expression:Array<String> - our expression
     *      output:TextView - view to show the answer
     */
    fun countAnswer(expression: Array<String>, output: TextView)
}

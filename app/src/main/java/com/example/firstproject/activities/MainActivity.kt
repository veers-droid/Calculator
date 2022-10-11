package com.example.firstproject.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.TextView
import com.example.firstproject.R
import com.example.firstproject.activities.info.AboutUs
import com.example.firstproject.service.counter.Counter
import com.example.firstproject.service.counter.impl.CounterImpl
import com.example.firstproject.service.output.OutputController
import com.example.firstproject.service.output.impl.OutputControllerImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bDecPoint
import kotlinx.android.synthetic.main.activity_main.bFolder
import kotlinx.android.synthetic.main.activity_main.isEqual
import kotlinx.android.synthetic.main.activity_main.onFactorial
import kotlinx.android.synthetic.main.activity_main.percent
import kotlinx.android.synthetic.main.activity_main.squareRoot
import kotlinx.android.synthetic.main.activity_main.toPower

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

class MainActivity : AppCompatActivity()
{
    // TextView used to display the input and output
    private lateinit var txtInput: TextView
    // story output shows our expressions with answers done before
    private lateinit var storyOutput: TextView
    // variable to handle our expression
    private var expression: Array<String> = arrayOf("")
    //classes to work with expression and output
    private lateinit var outputController: OutputController
    private lateinit var counter: Counter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initialise needed variables
        title = ""
        outputController = OutputControllerImpl()
        counter = CounterImpl()
        txtInput = findViewById(R.id.main_txt)
        storyOutput = findViewById(R.id.story_txt)


        bDecPoint.setOnClickListener()
        {
            if (isAnswer)//add answer for previous expression to story
            {
                storyOutput.append("\n=${txtInput.text}")
            }
            outputController.addDecimalPoint(txtInput, expression)
        }

        percent.setOnClickListener()
        {
            if (isAnswer)//add answer for previous expression to story
            {
                storyOutput.append("\n=${txtInput.text}")
            }
            counter.countPercent(txtInput, expression)
        }

        isEqual.setOnClickListener()
        {
            outputController.equalOutput(txtInput, expression, storyOutput)
        }

        bFolder.setOnClickListener()
        {
            engineeringMode()
        }

        onFactorial.setOnClickListener()
        {
            factorial()
        }

        toPower.setOnClickListener()
        {
            if (isAnswer)//add answer for previous expression to story
            {
                storyOutput.append("\n=${txtInput.text}")
            }
            outputController.addPower(txtInput, expression)
        }

        squareRoot.setOnClickListener()
        {
            if (isAnswer)//add answer for previous expression to story
            {
                storyOutput.append("\n=${txtInput.text}")
            }
            outputController.addRoot(txtInput, expression)
            isAnswer = false
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            R.id.aboutUs -> {
                val intent = Intent(this, AboutUs::class.java)
                startActivity(intent)
                return true
            }
            R.id.clear -> {
                storyOutput.text = ""
                outputController.del(true, txtInput, expression)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onNumberClick(view : View)//add on screen clicked number
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${txtInput.text}")
        }
        outputController.addNumber(view, txtInput, expression)
    }

    fun onOperationClick(view: View)//add on screen clicked operation
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${txtInput.text}")
        }
        outputController.addBasicOperationSymbol(view, txtInput, expression)
    }

    fun onDelClick(view: View)//handle delete button
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${txtInput.text}")
        }
        val button : Button = view as Button
        if (button.text.toString() == "C")
        {
            outputController.del(true, txtInput, expression)
        } else
        {
            outputController.del(false, txtInput, expression)
        }
    }

    fun onParenthesisClick(view: View)// add parenthesis
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${txtInput.text}")
        }
        outputController.addParenthesis(view, txtInput, expression)
    }

    fun onFunctionClick(view: View)//handle function buttons
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${txtInput.text}")
        }
        outputController.addFunction(view, txtInput, expression)
        isAnswer = false
    }

    //changing switch button name via res/strings does not work correct
    @SuppressLint("SetTextI18n")
    private fun engineeringMode()//switch to engineering mode
    {
        val buttonsArray = arrayOf(lnX, lgX, tgX, cosX, sinX)

        if (additionalRow.visibility == View.GONE)
        {
            additionalRow.visibility = View.VISIBLE

            for (button in buttonsArray)
            {
                button.visibility = View.VISIBLE
                button.startAnimation(TranslateAnimation(100f, 0f,
                    0f, 0f)
                    .apply {
                        duration = 275
                    })
            }
            bFolder.text = "fold"
        } else
        {
            additionalRow.visibility = View.GONE

            for (button in buttonsArray)
            {
                button.visibility = View.VISIBLE
                button.startAnimation(TranslateAnimation(0f, 50f,
                    0f, 0f)
                    .apply {
                        duration = 275
                    })
            }

            bFolder.text = "unfold"
        }
    }

    private fun factorial()// add on screen factorial sign
    {
        if (isAnswer)//add answer for previous expression to story
        {
            storyOutput.append("\n=${txtInput.text}")
        }
        // we cannot add factorial sign after parenthesis,
        // or arithmetical operation sign
        // and to empty text
        if (txtInput.text.isEmpty()
            || txtInput.text.last() == ')'
            || txtInput.text.last() == '('
            || outputController.isSign(txtInput.text.toString()))
        {
            return
        }
        //delete button if it last
        if (txtInput.text.last() == '.')
        {
            outputController.del(false, txtInput, expression)
        }

        txtInput.append("!")
        // need this type of factorial representation
        // to work properly with our library
        expression[0] += "!(1)"
    }
}

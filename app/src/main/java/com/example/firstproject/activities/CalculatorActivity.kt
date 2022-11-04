package com.example.firstproject.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.TextView
import com.example.firstproject.R
import com.example.firstproject.activities.converters.CurrencyConverterActivity
import com.example.firstproject.activities.info.AboutUs
import com.example.firstproject.calculator.Calculator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bFolder

private const val STORY_KEY:String = "STORY_KEY"
private const val OUTPUT_KEY:String = "OUTPUT_KEY"
private const val EXP_KEY:String = "EXP_KEY"
class MainActivity : AppCompatActivity()
{
    // TextView used to display the input and output
    private lateinit var txtInput: TextView
    // story output shows our expressions with answers done before
    private lateinit var storyOutput: TextView
    // in this class all business logic goes
    private lateinit var calculator: Calculator

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initialise needed variables
        title = ""
        txtInput = findViewById(R.id.main_txt)
        storyOutput = findViewById(R.id.story_txt)

        calculator = Calculator(txtInput, storyOutput)

        bFolder.setOnClickListener()
        {
            engineeringMode()
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
        return when (item.itemId)
        {
            R.id.converters -> {
                val intent = Intent(this, CurrencyConverterActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.aboutUs -> {
                val intent = Intent(this, AboutUs::class.java)
                startActivity(intent)
                return true
            }
            R.id.clear -> {
                storyOutput.text = ""
                calculator.del()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(OUTPUT_KEY, txtInput.text.toString())
        outState.putString(EXP_KEY, calculator.expression[0])
        outState.putString(STORY_KEY, storyOutput.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        txtInput.text = savedInstanceState.getString(OUTPUT_KEY)
        storyOutput.text = savedInstanceState.getString(STORY_KEY)
        calculator = Calculator(txtInput, storyOutput)
        calculator.expression = arrayOf(savedInstanceState.getString(EXP_KEY).toString())
    }

    fun handleButton(view: View)
    {
        calculator.handleButtonClick(view)
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
                button.visibility = View.GONE
                button.startAnimation(TranslateAnimation(0f, 50f,
                    0f, 0f)
                    .apply {
                        duration = 275
                    })
            }

            bFolder.text = "unfold"
        }
    }
}

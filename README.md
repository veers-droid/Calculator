# Calculator
## Contents
  - [Description](#description)
  - [Modes](#modes)
  - [Activities](#activities)
  - [Other features](#other-features)
## Description
This is a simple calculator app for android.</br>
Now only for portrait orientation
which can operate with such functions:
  * Basic mathematical operations (e.g. "+", "-", "*", "/")
  * Opeartions with parenthesis
  * Calculate functions (sin, cos, tan, ln, lg with base to 10 
  * It counts factorial 
  * gets the power and squared root of a number 
  
## Modes
My application has two modes:
  + [Simple mode](#simple-mode) 
  + [Engineering mode](#engineering-mode)
  
Also it has both dark and light themes which automaticaly switches depends on your device theme 
  
## Simple mode 
Provides us with basic arithmetical operations and percent counting</br>
![image](https://user-images.githubusercontent.com/56542846/195209419-58a16236-2aa5-467b-9a31-8e87dcedd5d6.png)</br>
image 1.1 the calculator menu in simple mode in light theme</br>
![image](https://user-images.githubusercontent.com/56542846/195209454-e2744168-5bd2-4639-b2c8-00d3b60c2071.png)</br>
image 1.2 the calculator menu in simple mode in hight theme
## Engineering mode
This mode provides us with other functions from previously listed</br>
![image](https://user-images.githubusercontent.com/56542846/195210482-9609fb5e-bb1b-4a8b-9903-90a5b02b3805.png)</br>
image 2.1  in engineering mode in light theme</br>
![image](https://user-images.githubusercontent.com/56542846/195210511-6baa268b-b102-4688-80d7-6574a7d197ac.png)</br>
image 2.2 the calculator menu in engineering mode in dark theme

## Activities
My android application has two activities for now
  * [Main Activity](#main-activity) (the activity where all the logic goes)
  * - [Services](#services) ( [output controller](#output-controller) and [counter](#counter) where realised our logic with counting written expression and creating expressions in right way)
  * [About_Us](#about-us) Activity (the one where the short info about the app shown)
  * [Menu](#menu) activities
  
## Main Activity
images 1 and 2 shows us how this activity looks.</br>
Let's talk about code, related to this activity.</br>
We have first of all the main activity kotlin class which uses to create our activity and show it to user</br>

      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
        
          setContentView(R.layout.activity_main)
To handle button clicks I used two ways</br>
Add in xml file for button "onClick" attribute which has the same purpose (like number buttons)</br>
And for single action buttons (such as switcher to engineering mode button) add in onCreate method such code:</br>

      buttonId.setOnclickListener() {
        //some buisness logic
      }
 ## Services
 We have two services wih appropriate intefrace for them.</br>
 let's have a quick look on interfaces withc suitable classes in service route
 ## Output Controller
 the first one interface <b>OutputController</b> has implementation with the such name <b>OutputControllerImpl</b></br>
 its root - <i>/services/output</i></br>
This service we need for creating beautiful expression for user and correct for our exp4j libbrary so it can parse and count correct</b></br>
There we have such functions:
*     fun addNumber(clicked: View, output: TextView, expression: Array<String>)
*     fun addBasicOperationSymbol(clicked: View, output: TextView, expression: Array<String>)
*     fun del(all: Boolean, output: TextView, expression: Array<String>)
*     fun addParenthesis(clicked: View, output: TextView, expression: Array<String>)
*     fun addDecimalPoint(output: TextView, expression: Array<String>)
*     fun equalOutput(output: TextView, expression: Array<String>, story: TextView)
*     fun addPower(output: TextView, expression: Array<String>)
*     fun addRoot(output: TextView, expression: Array<String>)
*     fun addFunction(clicked: View, output: TextView, expression: Array<String>)
*     fun isSign(num: String): Boolean
*     fun hasDot(num: String): Boolean
*     fun getLastNumber(exp: String): String
*     fun setFlagsAfterDel(exp: String)
Here you can see a short description about all functions
### addNumber
This function invokes after clicking by a digit button and adds a chosen digit to the end od our expression</br>
<b>Exceptions</b> (when we don't add a number or change our expression after adding):
+ If we trying to add number after getting our answer (isAnsswer flag is true) we erase answer and then add our number
### addBasicOperationSymbol
We call this function after clicking on signs of basic arithmetic operation to add it to the end of our expression</br>
<b>Exceptions:</b>
+ if our expression is empty we can add only an unary minus (thw same for open parenthesis)
+ to other binary operator we can add only unary minus, in other case we changes this operators (if we add unary minus we should add open parenthesis for better readability)
### del
This functions invokes after clicking by one of two del buttons and deletes one element of expression or all expression (depends on clicked button)</br>
<b>Specification:</b>
+ after deleting one element we must reset flags, if deleting all set all flags at false value
+ if we deleting "(" sign we must delete function name if we have
+ terminate function if the text is already empty
### addParenthesis
  This function adds open or close parenthesis to our expression</br>
  <b>Exceptions:</b>
  + If we add open parenthesis and the last symbol is dot, delete dot and then add parenthesis
  + We cannot add if the last symbol is not a numeric or if close parenthesis will be more than open one
  + We can't add close parenthesis if the expression is empty
### addDecimalPoint
  this funcction add dot sign to our number</br>
  <b>Exceptions:</b>
  + we cannot add dot sign if last symbol is not numeric
  + before adding we must check if the number already contains a dot because we cannot have 2 dots in one number
### equalOutput
  This function invokes Counter class ti count our expression after parsing our expression</br>
  <b>Sprecifications:</b>
  + remove from the end function without body
  + remove binary operators and dot from the end
  + add missing close parenthesis
  + add expression to story
  + invoke Counter class
### addPower
  This function adds power sign <b>"^"</b> to our expression workwith the same logic as <b>[addBasicOperationSymbol](#addbasicoperationsymbol)</b>
### addRoot
  This function adds root sign <b>"√"</b> to output expression and <b>"sqrt("</b> to our expression</br>
  <b>Exceptions:</b>
  +if the last symbol is dot delete it before adding root sign
### addFunction
  This function adds the functions to output and iths exp4j representation to expression 
### isSign
  This function implemented in interface to check is the last symbol is binary operator
### hasDot
  This function implemented in interface to check if our number has a dot
### getLastNumber
  This function is implemented in interfase to get a last number from expression for <b>hasDot</b> function
### setFlagsAfterDel
  This function implemented in interface to reset flags after <b>del</b> function invocation
## Counter
the first one interface <b>Counter</b> has implementation with the such name <b>CounterImpl</b></br>
 its root - <i>/services/counter</i></br>
 This service we use for calculating part or all our expression and show thr result to user</br>
 There we have such functions:
+      fun countPercent(input:TextView, expression:Array<String>)
+      fun countAnswer(expression: Array<String>, output: TextView)
Short description below:
### countPercent
This function gets last number drom expression, divides it by 100 and replaces this number with our answer</br>
<b>Excceptions:</b></br>
+ we can't count if our expression is empty
+ we can't count percent if last symbol is non-digit
### countAnswer
This function gets our expression if it can't count throw <i>ArithmeticException</i></br>
<b>Specification:</b></br>
+ we should remove ".0" tail for better look
+ we round answer to first 5 symbols after dot sign
## About Us 
It's a simple activity where we have scrolling text with short description about me and my app to leave this activity just tap on the screen
## Menu
At the moment I have only one menu activity for main activity in which we can:
+ clean the history
+ go to about us activity
This menu looks like this for both themes:</br>
![image](https://user-images.githubusercontent.com/56542846/195561056-5a278dce-3cb5-4623-a211-7ded57bf8a1d.png)</br>
image 3.1 menu activity in light theme</br>
![image](https://user-images.githubusercontent.com/56542846/195561158-647f2f85-5be4-4b35-8b1f-bcde6a169773.png)</br>
 image 3.2 menu activity in dark theme
## Other features
As you can guess my application has dark/light modes automaticaly sets by your system property.</br>
This setting written in App class which relates to all app the codesheet below:</br>

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
    }
    
Also I create animation for switching to enginnering mode
## Furthest versions
In next version I'm planning to add currency coverters and other converters (e.g. Area, length, weigth, etc.)</br>
and add album orientation

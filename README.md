# Calculator
## Contents
  - [Description](#description)
  - [Modes](#modes)
  - [Activities](#activities)
## Description
This is a simple calculator app for android</br>
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
![image](https://user-images.githubusercontent.com/56542846/195209419-58a16236-2aa5-467b-9a31-8e87dcedd5d6.png)
![image](https://user-images.githubusercontent.com/56542846/195209454-e2744168-5bd2-4639-b2c8-00d3b60c2071.png)

image 1.1 and image 1.2 the calculator menu in simple mode in light and hight themes
## Engineering mode
This mode provides us with other functions from previously listed</br>
![image](https://user-images.githubusercontent.com/56542846/195210482-9609fb5e-bb1b-4a8b-9903-90a5b02b3805.png)
![image](https://user-images.githubusercontent.com/56542846/195210511-6baa268b-b102-4688-80d7-6574a7d197ac.png)

image 2.1 and image 2.2 the calculator menu in engineering mode in light and dark themes

## Activities
My android application has two activities for now
  * [Main Activity](#main-activity) (the activity where all the logic goes)
  * - [Services](#services) ([output controller](output-controller) and counter where realised our logic with counting written expression and creating expressions in right way)
  * About_Us Activity (the one where the short info about the app shown)
  
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
This service we need for creating beautiful expression for user and correct for our exp4j libbrary so it can parse and count correct</b>
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
We call this function after clicking on signs of basic arithmetic operation to add it to the end of our expression
<b>Exceptions</b>
+ if our expression is empty we can add only an unary minus (thw same for open parenthesis)
+ to other binary operator we can add only unary minus, in other case we changes this operators (if we add unary minus we should add open parenthesis for better readability)
### del
This functions invokes after clicking by one of two del buttons and deletes one element of expression or all expression (depends on clicked button)
<b>Specification</b>
+ after deleting one element we must reset flags, if deleting all set all flags at false value
+ if we deleting "(" sign we must delete function name if we have
+ terminate function if the text is already empty
### addParenthesis
This function adds open or close parenthesis to our expression

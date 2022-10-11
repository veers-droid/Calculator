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
  * [Main Activity](#main-activity) (the activity where all the logic goes 
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
 

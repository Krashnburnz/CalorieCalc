package com.krashnburnz.caloriecalc.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import java.text.DecimalFormat;
import android.view.inputmethod.InputMethodManager;

//******************************************************************************
        /*  Java Class built for Calorie Calculator - Assignment #3.
            Author: Daniel Henderson
            Class: TCSS 498 Spring 2013
            School: University of Washington

            Calculations for Calories supplied by Instructor: Dr. Edwin Armstrong
        */
//******************************************************************************
public class MainActivity extends ActionBarActivity {
    private Button btnSlow;
    private Button btnMedium;
    private Button btnFast;
    private Button btnWalk;
    private Button btnRun;
    private Button btnCycle;
    private Button btnCalc;
    private InputMethodManager in;

    private EditText editTxtTime;
    private EditText editTxtWeight;

    private int pace = 1; //default is slow
    private int activity = 1; //default is walking

    private Context context;

    //Format to show only 2 decimal places with rounding
    private DecimalFormat df;

    Drawable drawableBlue;
    Drawable drawableRed;


    //******************************************************************************
        /*  Primary method that will create all the buttons, listeners, Edit Text fields
            etc.

            Buttons:
            There are 3 various Pace buttons for Slow, Medium, and Fast.
            There are 3 various Activity buttons for Walking, Running, and Cycling.
            There is one button to calculate the total calories based off the user input which
                will show a Alert Dialogue.

            EditTextFields:
            There are 2 Number EditText fields that will only accept numerical User input.
                Note: These fields must have values inside them from the user for the Calc button to work.
                    or a Toast Error message is given to the user to input values.

        */
    //******************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //Pace Buttons
        btnSlow = (Button) findViewById(R.id.buttonSlow);
        btnMedium = (Button) findViewById(R.id.buttonMedium);
        btnFast = (Button) findViewById(R.id.buttonFast);

        //Activity Buttons
        btnWalk =  (Button) findViewById(R.id.buttonWalking);
        btnRun = (Button) findViewById(R.id.buttonRunning);
        btnCycle =  (Button) findViewById(R.id.buttonCycling);

        //Calculate Button
        btnCalc = (Button) findViewById(R.id.buttonCalc);

        //EditText Fields for numbers
        editTxtTime = (EditText) findViewById(R.id.editTextTime);
        editTxtWeight = (EditText) findViewById(R.id.editTextWeight);

        context = getApplicationContext();
        df = new DecimalFormat("#,###,##0.00");

        Resources res = getResources();
        drawableBlue = res.getDrawable(R.anim.blue_button);
        drawableRed = res.getDrawable(R.anim.red_button);


        //******************************************************************************
        /*  Button Listeners for the various Paces such as Slow, Medium, or Fast.
            When each are clicked, it will set that activity button to RED, and turn the other
            2 pace buttons to blue. It will also set the pace variable to:
            Slow = 1, Medium = 2, Fast = 3
            Each of these integer values are using the when the calculation button is pressed.
        */
        //******************************************************************************
        btnSlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pace = 1;
                btnSlow.setBackgroundResource(R.anim.red_button);
                btnMedium.setBackgroundResource(R.anim.blue_button);
                btnFast.setBackgroundResource(R.anim.blue_button);
                dismissKeyboard(view);
            }
        });

        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pace = 2;
                btnSlow.setBackgroundResource(R.anim.blue_button);
                btnMedium.setBackgroundResource(R.anim.red_button);
                btnFast.setBackgroundResource(R.anim.blue_button);
                dismissKeyboard(view);
            }
        });

        btnFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pace = 3;
                btnSlow.setBackgroundResource(R.anim.blue_button);
                btnMedium.setBackgroundResource(R.anim.blue_button);
                btnFast.setBackgroundResource(R.anim.red_button);
                dismissKeyboard(view);
            }
        });


        //******************************************************************************
        /*  Button Listeners for the various Activities such as Walking, Running, or Cycling.
            When each are clicked, it will set that activity button to RED, and turn the other
            2 activity buttons to blue. It will also set the activity to:   Walking = 1, Running = 2, Cycling = 3
            Each of these integer values are using the when the calculation button is pressed.
        */
        //******************************************************************************
        btnWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity = 1;
                btnWalk.setBackgroundResource(R.anim.red_button);
                btnRun.setBackgroundResource(R.anim.blue_button);
                btnCycle.setBackgroundResource(R.anim.blue_button);
                dismissKeyboard(view);
            }
        });

        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity = 2;
                btnWalk.setBackgroundResource(R.anim.blue_button);
                btnRun.setBackgroundResource(R.anim.red_button);
                btnCycle.setBackgroundResource(R.anim.blue_button);
                dismissKeyboard(view);
            }
        });

        btnCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity = 3;
                btnWalk.setBackgroundResource(R.anim.blue_button);
                btnRun.setBackgroundResource(R.anim.blue_button);
                btnCycle.setBackgroundResource(R.anim.red_button);
                dismissKeyboard(view);
            }
        });

        //******************************************************************************
        /*  Button Listener for the calculate button: When the button is clicked, the 2
            EditText Number fields will be checked to ensure they are not empty. If either
            are empty, a message will be given to the user to input a value. If both have
            values, the calculation will happen, using the weight, time, pace, and activity
            which will be show to the User as an Alert Dialogue.

            Alert Dialogue: Title = Results, Message Example:  "Walking at a slow pace for 2.61 hours
            with a weight of 170 lbs. will burn 604.11 calories total."
        */
        //******************************************************************************
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(view);
                String weight = editTxtWeight.getText().toString();
                String time = editTxtTime.getText().toString();
                if(!weight.trim().isEmpty()) {
                    if(!time.trim().isEmpty()) {

                        CharSequence text = df.format(calcHours());
                        String answerHours = text.toString();


                        //Create Dialog message
                        String message = getActivity() + " at a " + getPace() + " pace for " + answerHours + " hours with a weight of " + weight + " lbs. will burn " + calcCaloriesBurned()+ " calories total. ";

                        showAlertDialog(message);
                        /*CharSequence text1 = "Please enter a value for 'Time' in minutes";
                        int duration = Toast.LENGTH_LONG;
                        for (int i=0; i < 5; i++)
                        {
                            Toast toast = Toast.makeText(context, message, duration);
                            toast.show();
                        }
                        */

                    } else {
                        CharSequence text1 = "Please enter a value for 'Time' in minutes";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text1, duration);
                        toast.show();

                    }

                } else {
                    CharSequence text2 = "Please enter a value for 'Weight' in pounds";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text2, duration);
                    toast.show();

                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //******************************************************************************
    /*  Method used to convert the Pace (Slow = 1, Medium = 2, fast = 3, from an integer
        to a String to be used in the Alert Dialogue upon clicking the calculate button.
     */
    //******************************************************************************
    public String getPace() {
        String answer = "";
        if(pace == 1) {

           answer = "slow";
        } else if(pace == 2) {

            answer = "medium";
        } else {
            answer = "fast";
        }
       return answer;
    }

    //******************************************************************************
    /*  Method used to convert the Activity (Walk = 1, Run = 2, Cycle = 3, from an integer
        to a String to be used in the Alert Dialogue upon clicking the calculate button.
     */
    //******************************************************************************
    public String getActivity() {
        String answer = "";
        if(activity == 1) {

            answer = "Walking";
        } else if(activity == 2) {

            answer = "Running";
        } else {
            answer = "Cycling";
        }
        return answer;
    }

    //******************************************************************************
    /*  Method used to calculate the totals hours taken from the input of minutes the user
        inputs. Returns a double value (not rounded) or formatted.

     */
    //******************************************************************************
    public double calcHours() {
        String value = editTxtTime.getText().toString();
        double minutes = Double.valueOf(value);
        double hours = minutes/60.00;
        return hours;
    }

    //******************************************************************************
    /*
    This is the primary method that calculates the total calories burned over a period of time (in hours).
    The weight the user inputs will be multiplied by the values below, then those results are multiplied
    by the total hours (calculated from the total minutes the user inputs) to get the final TOTAL of calories
    for this period of time, doing one of the 3 activities, at a certain pace. The total is then rounded
    to 2 decimal places.

    @returns String value representing the total calories burned

    Walking is Activity 1:
        Slow is pace 1: calories burned = (weight * 177)/130; per hour
        Medium is pace 2:  calories burned = (weight * 195)/130; per hour
        Fast is pace 3: calories burned = (weight * 224)/130; per hour

    Running is Activity 2:
        Slow is pace 1: calories burned = (weight * 472)/130; per hour
        Medium is pace 2:  calories burned = (weight * 590)/130; per hour
        Fast is pace 3: calories burned = (weight * 738)/130; per hour

    Cycling is Activity 3:
        Slow is pace 1: calories burned = (weight * 354)/130; per hour
        Medium is pace 2:   calories burned = (weight * 472)/130; per hour
        Fast is pace 3: calories burned = (weight * 590)/130; per hour

 */
    //******************************************************************************
    public String calcCaloriesBurned() {

        double doubleAnswer = 0.00;
        double weight = Double.valueOf(editTxtWeight.getText().toString());
        double doubleHours = calcHours();
        if(activity == 1) {
            if(pace == 1) {
                doubleAnswer = ((weight * 177)/130)*doubleHours;
            } else if(pace == 2) {
                doubleAnswer = ((weight * 195)/130)*doubleHours;
            } else if(pace == 3) {
                doubleAnswer = ((weight * 224)/130)*doubleHours;
            }

        } else if(activity == 2) {
            if(pace == 1) {
                doubleAnswer = ((weight * 472)/130)*doubleHours;
            } else if(pace == 2) {
                doubleAnswer = ((weight * 590)/130)*doubleHours;
            } else if(pace == 3) {
                doubleAnswer = ((weight * 738)/130)*doubleHours;
            }

        } else if(activity == 3) {
            if(pace == 1) {
                doubleAnswer = ((weight * 354)/130)*doubleHours;
            } else if(pace == 2) {
                doubleAnswer = ((weight * 472)/130)*doubleHours;
            } else if(pace == 3) {
                doubleAnswer = ((weight * 590)/130)*doubleHours;
            }

        }

        //Round to 2 decimal places and convert to a String to be returned
        CharSequence text = df.format(doubleAnswer);
        String answer = text.toString();
        return answer;
    }

    //Simple method to dismiss the keyboard
    public void dismissKeyboard(View view) {
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);//close soft keypad
    }

    public void showAlertDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Results");
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // here you could add functions
            }
        });

        //alertDialog.setIcon(R.drawable.icon);//could add icon if you'd like
        alertDialog.show();

    }

}

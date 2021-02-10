//MainActivity.java
//Calculates a bill total based on a tip percentage
package com.deitel.tipcalculator;

import android.support.v7.app.AppCompatActivity;    //Base class
import android.os.Bundle;   //for saving state information
import android.text.Editable;   //for EditText event handling
import android.text.TextWatcher; //EditText listener
import android.widget.EditText;  //for bill amount input
import android.widget.SeekBar;  //for changing the tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener;  //SeekBar listener
import android.widget.TextView;     //for displaying text

import java.text.NumberFormat;      //for currency formatting

//MainActivity class for the tip calculator app
public class MainActivity extends AppCompatActivity {

    //Current and percent formatter objects
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private double billAmount = 0.0;    //bill amount entered by the user
    private double percent = 0.15;      //initial tip percentage
    private TextView amountTextView;    //shows formatted bill amount
    private TextView percentTextView;   //shows tip percentage
    private TextView tipTextView;   //Shows calculate tip amount
    private TextView totalTextView; //shows calculate total bill amount

    //Called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //call superclass's version
        setContentView(R.layout.activity_main); //inflate the GUI

        //get references to programmatically manipulated TextViews
        amountTextView = findViewById(R.id.amountTextView);
        percentTextView = findViewById(R.id.percentTextView);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);
        tipTextView.setText(currencyFormat.format(0)); //set text to 0
        totalTextView.setText(currencyFormat.format(0));    //set text to 0

        //Set amountEditText's TextWatcher
        EditText amountEditText = findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        //Set percentSeekBar's OnSeekBarChangeListener
        SeekBar percentSeekBar = findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);

    }


    //Calculate and display tip and total amounts
    private void calculate(){
        //format percent and display in percentTextView
        percentTextView.setText(percentFormat.format(percent));

        //calculate the tip and total
        double tip = billAmount * percent;
        double total = billAmount + tip;

        //display tip and total formatted as currency
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
    }

    //listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    percent = progress/100.0;   //set percent based on progress
                    calculate();        //calculate and display tip and total
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };

    //listener object for the EditText's text-changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {

        //called when the user modifies the bill amount
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                //get bill amount and display currency formatted value
                billAmount = Double.parseDouble(s.toString());
                amountTextView.setText(currencyFormat.format(billAmount));
            }catch (NumberFormatException e){
                //if s is empty or non-numeric
                amountTextView.setText("");
                billAmount = 0.0;
            }
            calculate();        //update the tip and total TextViews
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}

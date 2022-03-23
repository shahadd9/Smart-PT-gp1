package com.example.smartpt;


import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;


public class PopUpWindowSession extends AppCompatActivity {

    RadioButton radioButton30, radioButton45, radioButton60, radioButtonElse;
    EditText editText, editTextFinished;
    String d;
    RadioGroup radioGroup;
    Button buttonSubmit;
    RadioButton selectedRadioButton;
    int finished = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window_session);


        ;

        editText = (EditText) findViewById(R.id.editToday);
        editText.setVisibility(View.INVISIBLE);

        // Get user data from popup dialog editeext.
        String duration = editText.getText().toString();


        editTextFinished = (EditText) findViewById(R.id.editDurationInToday);
        editTextFinished.setHint(finished);

        radioButton30 = (RadioButton) findViewById(R.id.M30);
        radioButton45 = (RadioButton) findViewById(R.id.M45);
        radioButton60 = (RadioButton) findViewById(R.id.M60);
        radioButtonElse = (RadioButton) findViewById(R.id.Melse);

        radioGroup = (RadioGroup) findViewById(R.id.groupToday);

        if ((radioButton30.isChecked())) {
            editText.setVisibility(View.INVISIBLE);

            editText.setInputType(InputType.TYPE_NULL);
            editText.setFocusableInTouchMode(false);
        } else if ((radioButton45.isChecked())) {
            editText.setVisibility(View.INVISIBLE);

            editText.setInputType(InputType.TYPE_NULL);
            editText.setFocusableInTouchMode(false);

        } else if ((radioButton60.isChecked())) {
            editText.setVisibility(View.INVISIBLE);

            editText.setInputType(InputType.TYPE_NULL);
            editText.setFocusableInTouchMode(false);
        } else {
            editText.setVisibility(View.VISIBLE);
            editText.setFocusableInTouchMode(true);
        }

        editTextFinished.setText(d);

        editTextFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d = editTextFinished.getText().toString();
                double dur = Double.parseDouble(d);
                Log.v("EditText", editTextFinished.getText().toString());


            }
        });


    }
}





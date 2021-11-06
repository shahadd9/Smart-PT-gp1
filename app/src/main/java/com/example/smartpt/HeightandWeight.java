package com.example.smartpt;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.smartpt.databinding.ActivityHeightandWeightBinding;

import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;


public class HeightandWeight extends AppCompatActivity {

    private Button btnToGoal;
//    private FloatingActionButton btnBackToBirthdate;
    private TextView qHandW;
    private TextView enterHeight;
    private TextView enterWeight;
    private TextView heightValue;
    private TextView weightValue;
    private String wUnit = "kg";
    private String hUnit = "cm";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heightand_weight);
        qHandW =findViewById(R.id.qHandW);
        btnToGoal=findViewById(R.id.toGoal);
//        btnBackToBirthdate=findViewById(R.id.backToBirthdate);
        enterWeight=findViewById(R.id.weight);
        enterHeight=findViewById(R.id.height);
        heightValue=findViewById(R.id.heightValue);
        weightValue=findViewById(R.id.weightValue);


//        backToBirthdate=findViewById(R.id.backToBirthdate);
//        humanBody=findViewById(R.id.body);
//        height=findViewById(R.id.seekBarHeight);
//        weight=findViewById(R.id.seekBarWeight);

//        //Set the height picker
//        final TextView heightPickerValueTv = findViewById(R.id.heightValue);

        final RulerValuePicker heightPicker = findViewById(R.id.height_ruler_picker);
        heightPicker.selectValue(156);
        heightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                heightValue.setText(selectedValue + " "+hUnit);
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                heightValue.setText(selectedValue + " "+hUnit);
            }
        });

        //Set the weight picker
//        final TextView weightPickerValueTv = findViewById(R.id.weightValue);
        final RulerValuePicker weightPicker = findViewById(R.id.weight_ruler_picker);
        weightPicker.setMinMaxValue(30, 250);
        weightPicker.selectValue(55);
        weightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                weightValue.setText(selectedValue + " "+wUnit);

            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                weightValue.setText(selectedValue + " "+wUnit);

            }
        });

//
//        btnBackToBirthdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    Intent intent= new Intent(HeightandWeight.this, Birthdate.class);
//                    startActivity(intent);
//
//            }
//
//
//        });
        btnToGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("myTag", "we are here");
                Intent intent = new Intent(HeightandWeight.this, Goal.class);
                startActivity(intent);

            }


        });
    }
//    public void backToBirthdate(){
//        Intent intent= new Intent(this, Birthdate.class);
//        startActivity(intent);
//    }

}

//        rulerValuePicker.setValuePickerListener(new RulerValuePickerListener() {
//            @Override
//            public void onValueChange(final int selectedValue) {
//                //Value changed and the user stopped scrolling the ruler.
//                //Application can consider this value as final selected value.
//            }
//
//            @Override
//            public void onIntermediateValueChange(final int selectedValue) {
//                //Value changed but the user is still scrolling the ruler.
//                //This value is not final value. Application can utilize this value to display the current selected value.
//            }
//        });


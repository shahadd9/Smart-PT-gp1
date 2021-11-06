package com.example.smartpt;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class Birthdate extends AppCompatActivity {

    private static final String TAG = "Birthdate";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
//    private FloatingActionButton btnBackToGender;
    private Button btnToHandW;
    private DatePicker picker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthdate);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
//        btnBackToGender= findViewById(R.id.backToGender);
        btnToHandW = findViewById(R.id.toHandW);


//        mDisplayDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog dialog = new DatePickerDialog(
//                        Birthdate.this,
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        mDateSetListener,
//                        year,month,day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
////            }
////        });
//
//        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                month = month + 1;
//                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
//
//                String date = month + "/" + day + "/" + year;
//                mDisplayDate.setText(date);
//            }
//        };

        picker=(DatePicker)findViewById(R.id.datePicker);



        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -100);
        Calendar calendar2 = Calendar.getInstance();

        calendar2.add(Calendar.YEAR, -13);
        long min = calendar.getTimeInMillis();
        long max = calendar2.getTimeInMillis();

        picker.setMinDate(min);
        picker.setMaxDate(max);

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        mDisplayDate.setText("Selected Date: "+ calendar.get(Calendar.DAY_OF_MONTH)+"/"+ calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR));

        picker.init(picker.getDayOfMonth(), (picker.getMonth() + 1), picker.getYear(), new DatePicker.OnDateChangedListener() {


            @Override
            public void onDateChanged(DatePicker datePicker, int dayOfMonth, int month, int year) {
                Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);
                mDisplayDate.setText("Selected Date: "+ picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear());

            }
        } );



//        btnBackToGender.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(Birthdate.this, Gender.class);
//                startActivity(intent);
//
//            }
//
//
//        });
        btnToHandW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("myTag", "we are here");
                Intent intent = new Intent(Birthdate.this, HeightandWeight.class);
                startActivity(intent);

            }


        });
    }
//    public void backToGender(){
//        Intent intent= new Intent(this, Gender.class);
//        startActivity(intent);
//    }

}


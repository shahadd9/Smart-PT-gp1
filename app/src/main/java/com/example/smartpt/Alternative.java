package com.example.smartpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class Alternative extends AppCompatActivity {

    private TextView lbl1,exName1,exName2, equ2, back;
    private Button exBtn1,exBtn2;
    private String name, SessionNo,level,exercise1,exercise2, excName1,excName2,ex1String,ex2String;
    private int index;
    String ex1[] = new String[4];
    String ex2[] = new String[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternative);

        //declare xml elements

        lbl1=findViewById(R.id.lbl1);
        exName1=findViewById(R.id.exName1);
        exName2=findViewById(R.id.exName2);
        equ2=findViewById(R.id.Equ2);
        back=findViewById(R.id.back);
        exBtn1=findViewById(R.id.exbtn1);
        exBtn2=findViewById(R.id.exbtn2);

        //get values from intent

        index=getIntent().getIntExtra("index",-1);
        SessionNo = getIntent().getStringExtra("SessionNo");
        name=getIntent().getStringExtra("name");
        level=getIntent().getStringExtra("level");


        //////////////////////////////////////////////////

        lbl1.setText(name+" Exercise Alternatives:");

//        findAlternative(name);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Alternative.this, PlanView.class);
                i.putExtra("SessionNo",SessionNo);
                i.putExtra("level",level);
                startActivity(i);
            }
        });



    }

    private void findAlternative(String name) {
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        // creating python object
        PyObject pyObj= py.getModule("myscript"); // call the python file

        PyObject alt1 = pyObj.callAttr("findAlternative1",name); // call the alt1 method in python
        exercise1 = alt1.toString();//retrieve  output
        /////
        PyObject alt2 = pyObj.callAttr("findAlternative2",name); // call the alt2 method in python
        exercise2 = alt2.toString();//retrieve  output

        //force,generalMuscle,name,equipment
        ex1String=exercise1.substring(2,exercise1.length()-3);
        ex1= ex1String.split("_");

        ex2String=exercise2.substring(2,exercise2.length()-3);
        ex2= ex2String.split("_");

    }
}
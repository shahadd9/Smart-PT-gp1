package com.example.smartpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TableProgress extends AppCompatActivity implements UpdateDialog.updateDialogListener {

    private int number;
    private DBHelper DB;
    private String dayAr[], globalName,globalSets,globalsRepa,globalWeight,setList[],weightList[],repsList[],message;
    private int idList[], postoupdate;
    private String SessionNo, level,currDay,duration,day;
    private ListView listView;
    private ArrayList<String> rows;
    private ArrayAdapter<String> adapter;
    private int id,week;
    private boolean flag;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_progress);
        currDay=getIntent().getStringExtra("currDay");
        week=getIntent().getIntExtra("week",0);
        SessionNo=getIntent().getStringExtra("SessionNo");
        level =getIntent().getStringExtra("level");

        DB=new DBHelper(this);
        idList =new int[100];
        setList=new String[100];
        repsList=new String[100];
        weightList=new String[100];
        flag=false;
        back=findViewById(R.id.backbtn);
        getex();
        Spinner spin = (Spinner) findViewById(R.id.spinnerD);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dayAr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(TableProgress.this,parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();

                getTable(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TableProgress.this, UserProgress.class);
                i.putExtra("SessionNo", SessionNo);
                i.putExtra("level", level);
                i.putExtra("week",week);
                i.putExtra("currDay",currDay);
                startActivity(i);
            }
        });
    }

    private void getex() {

        Cursor names =DB.getex();
        
        dayAr=new String[names.getCount()];
        if(names.getCount()==0){
            Toast.makeText(TableProgress.this,"No Progress For This Exercise",Toast.LENGTH_SHORT).show();

        }else {

            for(int i=0; i< dayAr.length;i++){

                if(names.moveToNext()) {
                    dayAr[i] = names.getString(0);
                }
            }



            }

        }

        public void getTable(String name){

        globalName= name;
        int counter=0;
            listView=findViewById(R.id.list);
            rows= new ArrayList<>();

            Cursor table =DB.getdata(name);

            if(table.getCount()==0){
                Toast.makeText(TableProgress.this,"No Progress For This Exercise",Toast.LENGTH_SHORT).show();

            } else{

                StringBuffer buffer;

                while (table.moveToNext()){
                    buffer=new StringBuffer();

                    idList[counter]=table.getInt(0);
                    setList[counter]=table.getString(3);
                    repsList[counter]=table.getString(4);
                    weightList[counter++]=table.getString(5);
                    buffer.append("date: "+table.getString(2)+"."+"\n");
                    buffer.append("sets: "+table.getString(3)+"."+"\n");
                    buffer.append("reps: "+table.getString(4)+"."+"\n");
                    buffer.append("weight: "+table.getString(5)+"."+"\n");
                    rows.add(buffer.toString());

                }

                adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rows){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){
                        /// Get the Item from ListView
                        View view = super.getView(position, convertView, parent);

                        TextView tv = (TextView) view.findViewById(android.R.id.text1);

                        // Set the text size 25 dip for ListView each item
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
                        tv.setBackgroundColor(Color.parseColor("#ECF9FD"));


                        // Return the view
                        return view;
                    }
                };
                listView.setAdapter(adapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        globalSets=setList[position];
                        globalsRepa=repsList[position];
                        globalWeight=weightList[position];
                        postoupdate=position;
                        String row= rows.get(position);
                        Toast.makeText(TableProgress.this,"selected",Toast.LENGTH_SHORT).show();
                        
                        openDialog();


                    }
                });



            }


        }

    private void openDialog() {

        UpdateDialog updateDialog=new UpdateDialog();
        updateDialog.show(getSupportFragmentManager(),"update dialog");


    }

    @Override
    public void applyText(String sets, String reps, String weight) {

        Log.d("sets:", sets);

        message="Enter correct fields";

        if(sets==null|| sets==" "||!sets.matches("[0-9]+")){

            flag=true;
            sets=globalSets;
            message=message+"\n sets should be a number";

        }
        if(reps==null|| reps==""||!reps.matches("[0-9]+")){

            flag=true;
            reps=globalsRepa;
            message=message+"\n reps should be a number";

        }
        if(weight==null|| weight==""||!weight.matches("[0-9]+")){

            flag=true;
            weight=globalWeight;
            message=message+"\n weight should be a number";

        }
        if(flag){
            Toast.makeText(TableProgress.this,message,Toast.LENGTH_LONG).show();

        }


        Boolean update =DB.updateuserdata(idList[postoupdate],sets,reps,weight);

        if(update){
            Toast.makeText(TableProgress.this,"saved",Toast.LENGTH_SHORT).show();
            getTable(globalName);

        }
else{
            Toast.makeText(TableProgress.this,"not saved",Toast.LENGTH_SHORT).show();

        }
    }
}
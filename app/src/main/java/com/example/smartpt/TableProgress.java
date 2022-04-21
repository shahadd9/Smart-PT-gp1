package com.example.smartpt;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class TableProgress extends AppCompatActivity implements UpdateDialog.updateDialogListener {

    private int number;
    private DBHelper DB;
    private String dayAr[], globalName;
    private int idList[], postoupdate;

    private ListView listView;
    private ArrayList<String> rows;
    private ArrayAdapter<String> adapter;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_progress);
        DB=new DBHelper(this);
        idList =new int[100];
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

                StringBuffer buffer=new StringBuffer();

                while (table.moveToNext()){

                    idList[counter++]=table.getInt(0);

                    buffer.append("date: "+table.getString(2)+"."+"\n");
                    buffer.append("sets: "+table.getString(3)+"."+"\n");
                    buffer.append("reps: "+table.getString(4)+"."+"\n");
                    buffer.append("weight: "+table.getString(5)+"."+"\n");
                    rows.add(buffer.toString());

                }

                adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, rows);
                listView.setAdapter(adapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        postoupdate=position;
                        String row= rows.get(position);
                        Toast.makeText(TableProgress.this,"No Progress For This Exercise",Toast.LENGTH_SHORT).show();
                        
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
package com.example.smartpt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DBHelper extends SQLiteOpenHelper {
    private FirebaseAuth uAuth = FirebaseAuth.getInstance();
    FirebaseUser curUser = uAuth.getCurrentUser();
    public  String idE = curUser.getEmail();
    private String user= idE.substring(0,idE.indexOf("@"));

    public DBHelper( Context context)
    {
        super(context, "progressDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create Table trainee(id integer primary key autoincrement, name TEXT,date TEXT, sets TEXT, reps TEXT, weight TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop Table if exists trainee");
    }

    public boolean insertuserdata(String name,String date, String sets, String reps, String weight){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues= new ContentValues();

        contentValues.put("name",name);
        contentValues.put("date",date);
        contentValues.put("sets",sets);
        contentValues.put("reps",reps);
        contentValues.put("weight",weight);

        long result= db.insert("trainee",null,contentValues);

        if(result ==-1){
            return false;
        }
        else {

            return true;
        }

    }

    public boolean updateuserdata( int id,String sets, String reps, String weight){

        String idS=id+"";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put("sets",sets);
        contentValues.put("reps",reps);
        contentValues.put("weight",weight);

        Cursor cursor=db.rawQuery("Select * from trainee where id = ?",new String[]{idS});

        if(cursor.getCount()>0) {
            long result = db.update("trainee", contentValues, "id=?", new String[]{idS});

            if (result == -1) {
                return false;
            } else {

                return true;
            }
        }else{

        }
        return false;
    }

    public Cursor getdata ( String name){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("Select * from trainee Where name =?",new String[]{name},null);


        return cursor;
    }

    public Cursor getex ( ){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("Select DISTINCT name from trainee",null);

        return cursor;
    }
}

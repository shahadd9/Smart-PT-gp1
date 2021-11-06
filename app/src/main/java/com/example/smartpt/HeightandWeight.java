package com.example.smartpt;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.example.smartpt.databinding.ActivityHeightandWeightBinding;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

import java.util.HashMap;
import java.util.Map;


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
    private FirebaseFirestore db;
    private String userIp;
    public static  int h;
    public static int w;


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
        db = FirebaseFirestore.getInstance();
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        userIp= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
h=156;
w=55;
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
                h=selectedValue;
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                heightValue.setText(selectedValue + " "+hUnit);
                h=selectedValue;
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
                w=selectedValue;

            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                weightValue.setText(selectedValue + " "+wUnit);
                w=selectedValue;

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
                Map<String,Object> user = new HashMap<>();
                user.put("height",h);
                user.put("weight",w);
                db.collection("userProfile").document(userIp).update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                           // Toast.makeText(Goal.this,"successful",Toast.LENGTH_SHORT);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(Goal.this,"Faild",Toast.LENGTH_SHORT);

                    }
                });
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


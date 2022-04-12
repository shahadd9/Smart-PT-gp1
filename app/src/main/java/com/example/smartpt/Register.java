package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private FirebaseAuth uAuth;
    private EditText email , pass;

    private TextView signup,login,c1,c2,c3,c4;
    private ImageView e1,e2,e3,e4;
    private boolean is8=false, hasupper=false,haslower=false,hasnum=false;
    public String user, pas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        uAuth= FirebaseAuth.getInstance();
        email=findViewById(R.id.regemail);
        pass=findViewById(R.id.regpass);

        signup=findViewById(R.id.signup);
        login=findViewById(R.id.Txtlogin);

        c1=(TextView) findViewById(R.id.con1);
        c2=(TextView)findViewById(R.id.con2);
        c3=(TextView)findViewById(R.id.con3);
        c4=(TextView)findViewById(R.id.con4);

        e1=(ImageView) findViewById(R.id.e1);
        e2=(ImageView) findViewById(R.id.e2);
        e3=(ImageView) findViewById(R.id.e3);
        e4=(ImageView) findViewById(R.id.e4);

        e1.setVisibility(View.INVISIBLE);
        e2.setVisibility(View.INVISIBLE);
        e3.setVisibility(View.INVISIBLE);
        e4.setVisibility(View.INVISIBLE);


        inputChanged();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
    }


    private void inputChanged(){
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passValidation();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @SuppressLint("ResourceType")
    private void passValidation() {
        user=email.getText().toString().trim();
        pas=pass.getText().toString().trim();
        //check length
        if(pas.length()>=8){
            is8=true;
            c1.setTextColor(Color.parseColor(getString(R.color.purple_500)));
            e1.setVisibility(View.VISIBLE);
        }else{
            is8=false;
            c1.setTextColor(Color.parseColor(getString(R.color.black)));
            e1.setVisibility(View.INVISIBLE);
        }
        //check number
        if(pas.matches(".*[0-9].*")){
            hasnum=true;
            c2.setTextColor(Color.parseColor(getString(R.color.purple_500)));
            e2.setVisibility(View.VISIBLE);
        }else{
            hasnum=false;
            c2.setTextColor(Color.parseColor(getString(R.color.black)));
            e2.setVisibility(View.INVISIBLE);
        }

        //check upperCase
        if(pas.matches(".*[A-Z].*")){
            hasupper=true;
            c3.setTextColor(Color.parseColor(getString(R.color.purple_500)));
            e3.setVisibility(View.VISIBLE);
        }else{
            hasupper=false;
            c3.setTextColor(Color.parseColor(getString(R.color.black)));
            e3.setVisibility(View.INVISIBLE);

        }

        //check lowerCase
        if(pas.matches(".*[a-z].*")){
            haslower=true;
            c4.setTextColor(Color.parseColor(getString(R.color.purple_500)));
            e4.setVisibility(View.VISIBLE);
        }else {
            haslower = false;
            c4.setTextColor(Color.parseColor(getString(R.color.black)));
            e4.setVisibility(View.INVISIBLE);

        }

    }

    private void register() {
        user=email.getText().toString().trim();
        pas=pass.getText().toString().trim();
        if(user.isEmpty()){
            email.setError("Email cannot be empty");
        }else  if(!Patterns.EMAIL_ADDRESS.matcher(user).matches()){
            email.setError("invalid email format");
        }
        if (pas.isEmpty()) {
            pass.setError("Password cannot be empty");
        }

        if (is8 && hasupper && haslower && hasnum) {
            uAuth.createUserWithEmailAndPassword(user, pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "Register success ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Name.class));
                    } else {
                        Toast.makeText(Register.this, "Register failed " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }else{
            pass.setError("Password not formatted correctly");
        }
    }

}
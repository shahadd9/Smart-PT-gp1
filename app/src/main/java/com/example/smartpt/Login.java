package com.example.smartpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Login extends AppCompatActivity {
    private FirebaseAuth uAuth;
    private EditText email , pass;
    private TextView register,login,forgot;
    private String id;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private String SessionNo;
    private String level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uAuth= FirebaseAuth.getInstance();
        email=findViewById(R.id.logemail);
        pass=findViewById(R.id.logpass);
        login=findViewById(R.id.login);
        register=findViewById(R.id.TxtRegister);
        forgot=findViewById(R.id.Txtforgot);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Forgot.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String user=email.getText().toString().trim();
        String pas=pass.getText().toString().trim();

        if(user.isEmpty()){
            email.setError("Email cannot be empty");
        }else  if(!Patterns.EMAIL_ADDRESS.matcher(user).matches()){
            email.setError("invalid email format");
        }
        if(pas.isEmpty()){
            pass.setError("Password cannot be empty");
        }

        else{
            uAuth.signInWithEmailAndPassword(user,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {

                        Toast.makeText(Login.this, "Login success ", Toast.LENGTH_SHORT).show();

                        getSessionLevel();
//                        startActivity(new Intent(Login.this, PlanView.class));
                    }else{
                        Toast.makeText(Login.this, "Login failed "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void getSessionLevel(){
        String user=email.getText().toString().trim();
        DocumentReference documentReference =  rootRef.collection("userProfile").document(user);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    level=value.getString("level");
                    SessionNo=value.getString("TrainingdaysNum");
                    Intent i = new Intent(Login.this, PlanView.class);
                    i.putExtra("SessionNo", SessionNo);
                    i.putExtra("level", level);
                    startActivity(i);

                } else {
                    Toast.makeText(Login.this, "Document not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    }

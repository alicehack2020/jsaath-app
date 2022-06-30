package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

 import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText Email,Password;
    Button Login;
    TextView userLogin;

    String email,password;
    String emailPattern = "[a-zA-Z0-9._-]+@[Gmail,gmail,GMAIL]+\\.+[Com,com,COM]+";

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        Email=findViewById(R.id.userEmail);
        Password=findViewById(R.id.userPassword);
        Login=findViewById(R.id.login);
        userLogin=findViewById(R.id.userLogin);




        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });




        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                email=Email.getText().toString();
                password=Password.getText().toString();
                if(email.isEmpty())
                {
                    Toast.makeText(Login.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty())
                {
                    Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else
                    {

                        if (email.trim().matches(emailPattern))
                        {
                              Logindata();
                        }
                        else
                        {
                              Toast.makeText(Login.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                        }
                }


            }
        });
    }

    private void Logindata()
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent=new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                         } else {


                              Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                         }


                    }
                });
    }
}

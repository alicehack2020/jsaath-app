package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUp extends AppCompatActivity{

    EditText Name,email,phone,Password;
    Button register;
    String UserEmail,UserPhone,username,password,transactionid,currentDateandTime;
    String emailPattern = "[a-zA-Z0-9._-]+@[Gmail,gmail,GMAIL]+\\.+[Com,com,COM]+";
    FirebaseAuth mAuth;
    TextView userRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();


        Name=findViewById(R.id.userName);
        phone=findViewById(R.id.userMobile);
        Password=findViewById(R.id.userPassword);
        email=findViewById(R.id.userEmail);

        register=findViewById(R.id.register);
        userRegister=findViewById(R.id.userRegister);




        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        currentDateandTime = sdf.format(new Date());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                username=Name.getText().toString();
                UserEmail=email.getText().toString();
                UserPhone=phone.getText().toString();
                password=Password.getText().toString();

                if (username.isEmpty())
                {
                    Toast.makeText(SignUp.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }

                else if(UserPhone.isEmpty())
                {
                    Toast.makeText(SignUp.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                }
                else if(UserPhone.length()<10)
                {
                    Toast.makeText(SignUp.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                }
                else if(UserPhone.length()>10)
                {
                    Toast.makeText(SignUp.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                }
                else if(UserEmail.isEmpty())
                {
                    Toast.makeText(SignUp.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(UserEmail.trim()))
                {
                    Toast.makeText(SignUp.this,"Invalid Email Address", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<=5)
                {
                    Toast.makeText(SignUp.this, "Enter Strong Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (UserEmail.trim().matches(emailPattern))
                    {
                        start();
                    }
                    else
                    {
                        Toast.makeText(SignUp.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
    }

    public void start ()
    {
        mAuth.createUserWithEmailAndPassword(UserEmail,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            String uid=user.getUid();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference Name = database.getReference("MainUser").child(uid).child("Name");
                            Name.setValue(username);

                             DatabaseReference Mobile = database.getReference("MainUser").child(uid).child("Mobile");
                            Mobile.setValue(UserPhone);

                             DatabaseReference Email = database.getReference("MainUser").child(uid).child("Email");
                            Email.setValue(UserEmail);



                            DatabaseReference TransactionDate = database.getReference("MainUser").child(uid).child("Registration Date");
                            TransactionDate.setValue(currentDateandTime);




                            Toast.makeText(SignUp.this, "Registration Done",
                                    Toast.LENGTH_SHORT).show();

                            //Intent intent=new Intent(SignUp.this, Flash.class);
                            //startActivity(intent);
                            //finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

}

package com.example.prhyt.laundryuser;

// For password reset of the user's email

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class password extends AppCompatActivity {

    private Button reset,login;
    private EditText email;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        setID();

        firebaseAuth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login_page.class));
                finish();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail= email.getText().toString().trim();
                if(useremail.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter the valid email id",Toast.LENGTH_LONG).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Check your email for password reset link!",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"The password reset link could not be sent. Try Again!!!!!!!!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private void setID(){
        reset = findViewById(R.id.resetr);
        login = findViewById(R.id.login2);
        email = findViewById(R.id.emailr);
    }
}

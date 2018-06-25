package com.example.prhyt.laundryuser;

// Login Page for the App user

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_page extends AppCompatActivity {

    public Button login,signin;
    //public TextView tv_info;
    public EditText user_email,password;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseauth;
    private Button passwordreset;
    //private TextView forgot_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        setID();

        firebaseauth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        FirebaseUser firebaseuser = firebaseauth.getCurrentUser();


        passwordreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k;
                k = new Intent(getApplicationContext(),password.class);
                startActivity(k);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass;
                email = user_email.getText().toString();
                pass = password.getText().toString();

                if(validate(email,pass))
                {
                    check(email,pass);
                }

                else
                    ;
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login_page.this);
// Setting Dialog Title
                alertDialog.setTitle("Info");

// Setting Dialog Message
                alertDialog.setMessage("To get another account, or if you are facing any problem, contact the developer: +91 7046669471");

// Setting Icon to Dialog


// Setting OK Button
                alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        //Toast.makeText(getApplicationContext(), "To get another account, or if you are facing any problem, contact the developer: +91 7046669471", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialogMain = alertDialog.create();

// Showing Alert Message
                alertDialogMain.show();
            }
        });

    }

    public void setID(){
        login = findViewById(R.id.login2);
        //tv_info = findViewById(R.id.notLoggedIn);
        user_email = findViewById(R.id.Email);
        password = findViewById(R.id.userPassword);
        signin = findViewById(R.id.notSignedIn);
        passwordreset = findViewById(R.id.btn_1);
    }

    public boolean validate(String name, String pass){
        if(name.isEmpty() || pass.isEmpty()){
            Toast.makeText(getApplicationContext(),"Enter the valid details!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    public void check(String email, String pass){

        progressDialog.setMessage("Loading");
        progressDialog.show();

        firebaseauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Logged IN!!!!!!!!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),mainInfoPage.class));
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Login failed!!!!!!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}

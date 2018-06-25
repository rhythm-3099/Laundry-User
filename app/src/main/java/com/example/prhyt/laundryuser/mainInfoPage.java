package com.example.prhyt.laundryuser;

// Provides an overview of all the data of the user

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mainInfoPage extends AppCompatActivity {

    private TextView username,useremail,userCost,userClothes,userBalance;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private Button btn_clothes;
    private TextView datech;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info_page);
        setId();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        DatabaseReference myRefProfile = firebaseDatabase.getReference(firebaseAuth.getUid());

        myRefProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation userProfile = dataSnapshot.getValue(UserInformation.class);
                username.setText(userProfile.getUsername());
                useremail.setText(userProfile.getUseremail());
                String clothes,balance,cost;
                clothes = Integer.toString(userProfile.getClothes());
                balance = Double.toString(userProfile.getBalance());
                cost = Double.toString(userProfile.getCost());
                userClothes.setText(clothes);
                userBalance.setText(balance);
                userCost.setText(cost);
                datech.setText(userProfile.getDate().toString());
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        btn_clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),infoUpdate.class));
                finish();
            }
        });

    }

    private void setId(){
        username = findViewById(R.id.username);
        useremail = findViewById(R.id.useremail);
        userCost = findViewById(R.id.userCost);
        userClothes = findViewById(R.id.infoClothes);
        userBalance = findViewById(R.id.userBalance);
        btn_clothes = findViewById(R.id.clothesUpdate);
        datech = findViewById(R.id.lastUpdate);
    }
}

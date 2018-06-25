package com.example.prhyt.laundryuser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class infoUpdate extends AppCompatActivity {

    private Button update_btn;
    private EditText clothes;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private int numClothes;
    private double numCost,numBalance;
    private double costforuser;
    private String username,useremail,userpassword,datechange;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_update);


        update_btn = findViewById(R.id.updateInfobtn);
        clothes = findViewById(R.id.infoClothes);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation userinfo = dataSnapshot.getValue(UserInformation.class);
                numCost = userinfo.getCost();
                numBalance = userinfo.getBalance();
                numClothes = userinfo.getClothes();
                username = userinfo.getUsername();
                useremail = userinfo.getUseremail();
                userpassword = userinfo.getUserpassword();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Could not update data!",Toast.LENGTH_SHORT).show();
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s,day,month,year,hours,mins,secs;
                Calendar calendar;
                calendar = new GregorianCalendar();
                int[] arr = {1,2,3,4,5,6,7,8,9,10,11,12};
                //int num_month;
                //num_month= calendar.get(Calendar.MONTH);
                //num_month++;
                hours = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
                mins = Integer.toString(calendar.get(Calendar.MINUTE));
                secs = Integer.toString(calendar.get(Calendar.SECOND));
                day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
                month = Integer.toString(arr[calendar.get(Calendar.MONTH)]);
                year = Integer.toString(calendar.get(Calendar.YEAR));

                s = hours + ":" + mins + ":" + secs + "    " + day + "/" + month + "/" + year;

                int number;
                if (check(clothes.getText().toString())) {
                    numCost = numCost + (Integer.parseInt(clothes.getText().toString()))*4;
                    number = Integer.parseInt(clothes.getText().toString());
                    number = numClothes + number;
                    UserInformation userinfo = new UserInformation(username, useremail, userpassword, number, numBalance, numCost,s);

                    databaseReference.setValue(userinfo);
                    startActivity(new Intent(getApplicationContext(), mainInfoPage.class));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter a number!",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public boolean check(String s){
        int i;
        boolean ans=true;
        for(i=0;i<s.length();i++){
            if(s.charAt(i)<48 || s.charAt(i)>57)
            {
                ans=false;
                break;
            }
        }
        return ans;
    }
}

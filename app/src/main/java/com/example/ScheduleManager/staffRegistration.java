package com.example.ScheduleManager;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class staffRegistration extends AppCompatActivity {
    EditText fname,lname,userName,sreenName,password,password2;
    int  ii=0;

    Button register,goToEvent;
    FirebaseAuth firebaseAuth;

        FirebaseDatabase firebaseDatabase  = FirebaseDatabase.getInstance();

        DatabaseReference ref = firebaseDatabase.getReference("staff");
        Staffs staff = new Staffs();

        MainActivity mainActivity = new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_registration);

        fname=findViewById(R.id.sfname);
        lname=findViewById(R.id.slname);
        goToEvent=findViewById(R.id.gotoEvent);

        userName=findViewById(R.id.userName);
        password=findViewById(R.id.password);
        password2=findViewById(R.id.password2);
        register=findViewById(R.id.staffRegButton);
        sreenName=findViewById(R.id.screenName);
        registerSftuff();
        openEventset();
    }

    private void openEventset(){
        goToEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(staffRegistration.this,staffLogin.class));
               // startActivity(new Intent(staffRegistration.this,setEvent.class));

            }
        });

    }
    String key;

    boolean existUserName;
   ArrayList<String> keys;
    private void registerSftuff(){
         existUserName=false;

        keys=new ArrayList<String>();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name = fname.getText().toString();

                String last_name = lname.getText().toString();
                String user_name = userName.getText().toString();
                String screen_name = sreenName.getText().toString();
                String passwordd = password.getText().toString();
                if (first_name.isEmpty()) {
                    fname.setError("First Name is Required");
                    fname.requestFocus();
                    return;
                }
                if (last_name.isEmpty() || user_name.isEmpty() || passwordd.isEmpty() || screen_name.isEmpty()) {
                    mainActivity.displayMessage("All fields are required!!", staffRegistration.this);
                    return;
                }


                try {
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                key = dataSnapshot1.getKey();
                                keys.add(key);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (Exception e) {
                    mainActivity.displayMessage("somthing went wrong in firebase", staffRegistration.this);
                }


                try {
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                keys.clear();
                            } catch (Exception e) {

                                mainActivity.displayMessage(e.toString(), staffRegistration.this);
                            }
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                key = dataSnapshot1.getKey();
                                keys.add(key);
                                ii = 1;
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (Exception e) {
                    mainActivity.displayMessage("somthing went wrong in firebase", staffRegistration.this);
                }

                if (keys.contains(user_name) ) {
                    mainActivity.displayMessage("User Name Unavailable", staffRegistration.this);
                    return;
                }

                staff.setFname(first_name);
                staff.setLname(last_name);
                staff.setUserName(user_name);
                staff.setPassword(passwordd);
                staff.setScreenName(screen_name);
                try {
                    ref.child(staff.getUserName()).setValue(staff);
                    startActivity(new Intent(staffRegistration.this,staffLogin.class));
                } catch (Exception e) {
                    mainActivity.displayMessage(e.toString(), staffRegistration.this);

                }
//                try {
//                    mainActivity.displayMessage("ath_start", staffRegistration.this);
//                    firebaseAuth = FirebaseAuth.getInstance();
//                    firebaseAuth.createUserWithEmailAndPassword(user_name, passwordd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful())
//                                mainActivity.displayMessage("Success!!", getApplicationContext());
//                            else
//                                mainActivity.displayMessage("Failed!!", getApplicationContext());
//                        }
//                    });
//
//                } catch (Exception e) {
//                    mainActivity.displayMessage("Error on Authentication::" + e.toString(), staffRegistration.this);
//
//                }

//try {
//    mainActivity.displayMessage("Email is " + staff.getUserName() + "Staff Added !!  " + keys.get(0)+" "+keys.get(1), staffRegistration.this);
//}catch (Exception e){
//    mainActivity.displayMessage(e.toString(),staffRegistration.this);
//}}
            }});

    }


}

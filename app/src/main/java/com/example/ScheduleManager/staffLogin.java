package com.example.ScheduleManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class staffLogin extends AppCompatActivity {

    EditText userName,password;
    TextView loginButton;
    String key;
    FirebaseDatabase firebaseDatabase  = FirebaseDatabase.getInstance();
    int iii=0;
    String userNameCalled;

    DatabaseReference ref = firebaseDatabase.getReference("staff");
    MainActivity mainActivity = new MainActivity();
    boolean logedIn=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);
        userName=findViewById(R.id.txtEmail);
        password=findViewById(R.id.txtPwd);
        loginButton=findViewById(R.id.btnLogin);

loginButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        iii++;

        try {
            logedIn=false;
            ref.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//


                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                       // key= dataSnapshot1.getKey();
                        Staffs staff = dataSnapshot1.getValue(Staffs.class);
                        if(staff.getUserName().equals(userName.getText().toString()) && staff.getPassword().equals(password.getText().toString())) {
                            logedIn = true;
                            Intent intent=new Intent(staffLogin.this,setEvent.class);
                            intent.putExtra("passedUserName",staff.getUserName());
                            startActivity(intent);
                        userNameCalled=staff.getUserName();

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            if(!logedIn){
                mainActivity.displayMessage("Access denied!!",staffLogin.this);
            }

        }catch (Exception e){
            mainActivity.displayMessage("somthing went wrong in firebase",staffLogin.this);
        }

//        if(logedIn) {
//            mainActivity.displayMessage(userNameCalled+" Access Allowed!!",staffLogin.this);
//            logedIn=false;
//        }


    }









//        loginFuction();
//        if(loginVariable){
//
//            mainActivity.displayMessage("Access Allowed!!",staffLogin.this);
//            loginVariable=false;
//        }
//
//       else{
//
//            mainActivity.displayMessage("Access denied!!",staffLogin.this);
//        }
   // }
});

    }


}

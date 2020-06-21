package com.example.ScheduleManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.projecttest.notifications.Client;
//import com.example.projecttest.notifications.Token;
//import com.google.android.gms.common.api.Api;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.RemoteMessage;

public class userRegistration extends AppCompatActivity {
    EditText id,fname,lname,province,district,sector,cell;
    Button register,show;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    DatabaseReference ref ;
    String tokenId;
    Citizens citizens = new Citizens();
    MainActivity mainActivity=new MainActivity();
    //FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
   // APIService apiService ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        register = findViewById(R.id.citizenRegButton);
        ref = firebaseDatabase.getReference("Citizens");
        try {

            addCitizen();
          //  showData();
        }catch (Exception e){
            Toast.makeText(this, e.toString(),Toast.LENGTH_LONG).show();
        }

//        show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showFunction();
//
//            }
//        });
       // apiService=Client.getRetrofit("https://fcm.googleapis.com/").create(APIService.class);
    }


    private void addCitizen(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCitizen();
            }
        });

    }

    public void showData(){


        DatabaseReference databaseRef=  FirebaseDatabase.getInstance().getReference("Notifications").push();

       FirebaseMessaging fm = FirebaseMessaging.getInstance();
//        fm.send(new RemoteMessage.Builder(SENDER_ID + "@fcm.googleapis.com")
//                .setMessageId(Integer.toString(messageId))
//                .addData("my_message", "Hello World")
//                .addData("my_action","SAY_HELLO")
//                .build());



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //StringBuilder stringBuilder=new StringBuilder();

                StringBuffer stringBuffer=new StringBuffer();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Citizens citizen= dataSnapshot1.getValue(Citizens.class);
                     stringBuffer.append(citizen.getFname()+"\n");
                }
                mainActivity.displayMessage(stringBuffer.toString(),userRegistration.this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

       // updatToken(FirebaseInstanceId.getInstance().getToken());
    }

//public void updatToken(String tokenn){
//
//  //  FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Tokens");
//    Token token=new Token(tokenn);
//  //  databaseReference.child(firebaseUser.getUid()).setValue(token);
//}

    public void showFunction(){

                startActivity(new Intent(userRegistration.this,timeTableList.class));

    }


    private void registerCitizen(){


       try {
           id = findViewById(R.id.id);

           fname = findViewById(R.id.fname);
           lname = findViewById(R.id.lname);
           province = findViewById(R.id.province);
           district = findViewById(R.id.district);
           sector = findViewById(R.id.sector);
           cell = findViewById(R.id.cell);

           FirebaseMessaging.getInstance().subscribeToTopic(district.getText().toString());
           mainActivity.displayMessage("Subscription success!!",userRegistration.this);

           if(fname.getText().toString().isEmpty()){
               fname.setError("First Name is Required");
               fname.requestFocus();
               return;
           }
           if(lname.getText().toString().isEmpty()||province.getText().toString().isEmpty()||sector.getText().toString().isEmpty()
                   ||district.getText().toString().isEmpty()
       ||cell.getText().toString().isEmpty()){
               mainActivity.displayMessage("All fields are required!!",userRegistration.this);
               return;
           }

           FirebaseInstanceId.getInstance().getInstanceId()
                   .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                       @Override
                       public void onComplete(@NonNull Task<InstanceIdResult> task) {
                           if (task.isSuccessful()) {
                               tokenId = task.getResult().getToken();
                               // saveToken(token);
                           } else {
                               tokenId = "Token not found";
                               return;
                           }
                       }
                   });

           citizens.setToken(tokenId);
           citizens.setFname(fname.getText().toString());
           citizens.setLname(lname.getText().toString());
           citizens.setProvince(province.getText().toString());
           citizens.setSector(sector.getText().toString());
           citizens.setSector(sector.getText().toString());
           citizens.setDistrict(district.getText().toString());
           citizens.setCell(cell.getText().toString());



String completeEmail="@gmail.com";

String completePassword="1234567";


         //  mainActivity.displayMessage("ath_start",staffRegistration.this);
           firebaseAuth= FirebaseAuth.getInstance();
           firebaseAuth.createUserWithEmailAndPassword(id.getText().toString()+completeEmail,completePassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()) {
                       mainActivity.displayMessage("User Authenticated", getApplicationContext());

                   }

                   else {
                       mainActivity.displayMessage("User Authentication Failed!!", getApplicationContext());
                       return;
                   }
               }
           });


           ref.child(id.getText().toString()).setValue(citizens);
           Toast.makeText(userRegistration.this,"User is Added!!"+tokenId,Toast.LENGTH_LONG).show();
       }catch (Exception e){
           Toast.makeText(userRegistration.this,e.toString(),Toast.LENGTH_LONG).show();
       }

    }

}

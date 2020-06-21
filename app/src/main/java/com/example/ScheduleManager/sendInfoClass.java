package com.example.ScheduleManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sendInfoClass extends AppCompatActivity {
EditText info;
Button sendInfo;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference ref1 ;
    DatabaseReference ref ;
String title;
String body;
String owner;
final static int SEND_SMS_PERMISSION_REQUEST_CODE=111;
    Api api;
    String tokenn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_info_class);

        info=findViewById(R.id.messagToSnd);

        sendInfo=findViewById(R.id.sendeNot);
        ref = firebaseDatabase.getReference("Citizens");
        ref1 = firebaseDatabase.getReference("messages");
title="You Have A new Notification";


        sendButtonClicked();

    }





    public void sendButtonClicked(){

        sendInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date=new Date();

                String currentdate=sdf.format(date);

                body=info.getText().toString();
                message Message=new message();
                Message.setMessagBody(body);
                Message.setTimeSent(currentdate);
                Bundle bundle=getIntent().getExtras();
                owner=bundle.getString("stringToPasss");
                ref1.child(owner).push().setValue(Message);
           Toast.makeText(getApplicationContext(),"Message Sent ",Toast.LENGTH_SHORT);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://projecttest-dcc6e.firebaseapp.com/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);

                Call<ResponseBody> call = api.sendNotification("dMEYjfskvhc:APA91bE7OnG7bYhbfoYb3g_qTiAUNRQYvuqlfqt_DaSeLsP6LZoqAtbt8mEBRMhQiFDFfIGepKCsI_pa9FfVpPoafOGnEEUfJxXavkGj-F1uSzZZsydJKUpg8x99BQbpCuN1SfMYUbcS", title, body);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Toast.makeText(getApplicationContext(), response.body().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });






//                Retrofit retrofit=new Retrofit.Builder()
//                        .baseUrl("https://projecttest-dcc6e.firebaseapp.com/api/")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//    api=retrofit.create(Api.class);
//
//
//
//ref.addValueEventListener(new ValueEventListener() {
//    @Override
//    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//            Citizens ctz = dataSnapshot1.getValue(Citizens.class);
//            tokenn=ctz.getToken();
//      Call<ResponseBody> call =api.sendNotification(tokenn,title,body);
//call.enqueue(new Callback<ResponseBody>() {
//    @Override
//    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//        try {
//            Toast.makeText(getApplicationContext(),response.body().string()+"And the token is "+tokenn,Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//    }
//});
//        }
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//    }
//});



//if(checkPermission(Manifest.permission.SEND_SMS)){
//int ioo=1;
//}

   ActivityCompat.requestPermissions(sendInfoClass.this,new String []{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);


final String msg=body;

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Citizens ctz = dataSnapshot1.getValue(Citizens.class);
                            tokenn = ctz.getCell();

                            String phoneNumber = tokenn;
                            if (checkPermission(Manifest.permission.SEND_SMS)) {

                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
                                //Toast.makeText(getApplicationContext(),"Sent to "+phoneNumber,Toast.LENGTH_SHORT).show();
                            } else {
                                  Toast.makeText(getApplicationContext(),"Access denied ",Toast.LENGTH_SHORT).show();
                            }

                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });






            }
        });




    }


    public boolean checkPermission(String permission){
        int chkp= ContextCompat.checkSelfPermission(this,permission);
        return chkp== PackageManager.PERMISSION_GRANTED;

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    switch (requestCode){
//
//        case SEND_SMS_PERMISSION_REQUEST_CODE:
//            if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//
//                int ifg=23;
//
//            }
//            break;
//            }
//    }


}

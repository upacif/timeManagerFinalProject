package com.example.ScheduleManager;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static final String CHANNEL_ID = "myChanelId";
    private static final String CHANNEL_NAME = "myChanelName";
    private static final String CHANNEL_DESC = "myChanelDescription";
String [] itemList;
     Toolbar toolbar;
     private DrawerLayout drawerLayout;
    public NavigationView navigationView;
     private ListView listView;
     ProgressBar pb;
     int count=0;
     private EditText inputItem ;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference ref = firebaseDatabase.getReference("staff");
    ArrayList<String> staffPosts= new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        pb = findViewById(R.id.myProgressBar);
         arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, staffPosts);
        listView.setAdapter(arrayAdapter);

       // prog();
        showData();
        showListView();
        toolbar = findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigation);
        }

        drawerLayout=findViewById(R.id.drawer_layout);
        shoNavigation();

    }

//public void prog() {
//
//    final Timer t = new Timer();
//   TimerTask tt=new TimerTask() {
//       @Override
//       public void run() {
//           count++;
//         pb.setProgress(count);
//         if(count==100){
//             t.cancel();
//
//         }
//       }
//   };

//   t.schedule(tt,0,100);
//}
    private void shoNavigation(){


        navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.downloadId:
                        item.setChecked(true);
                        displayMessage("Download is atarted!",MainActivity.this);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.messageId:
                        item.setChecked(true);
                        displayMessage("Message is aterted!",MainActivity.this);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.vdeoId:
                        item.setChecked(true);

                        startActivity(new Intent(MainActivity.this,staffRegistration.class));
                        return true;
                    case R.id.registerId:
                        item.setChecked(true);
                        Intent intent =new Intent(MainActivity.this,userRegistration.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        return true;

                }
                return false;
            }
        });


    }

    private void showListView(){

       try {


         //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, staffPosts);
         //  listView.setAdapter(arrayAdapter);

           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                       String value = (String) listView.getItemAtPosition(position);
                           Intent intent = new Intent(MainActivity.this, DialogActivity.class);

                           intent.putExtra("stringToPass", value);

                           startActivity(intent);


                       }
                   });

       }catch(Exception e)
       {
           displayMessage( " somthing went wrong", MainActivity.this);
       }
    }


    public void displayMessage(String message, Context context){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

//    public boolean userNameExist(final String userName){
//        try {
//
//
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        String key= dataSnapshot1.getKey();
//                        if(key.equals(userName)){
//                            existUserName=true;
//                        }
//
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }catch (Exception e){
//            displayMessage(" somthing went wrong in firebase", MainActivity.this);
//        }
//
//        if(existUserName){
//            return true;
//        }
//        else {
//            return false;
//        }
//
//    }

    public void showData(){


        try {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //StringBuilder stringBuilder=new StringBuilder();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                       String key= dataSnapshot1.getKey();
                        Staffs staff = dataSnapshot1.getValue(Staffs.class);

                        if (staff != null) {
                            staffPosts.add(staff.getUserName());
                            arrayAdapter.notifyDataSetChanged();
                        }
                        pb.setProgress(++count);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            displayMessage(" somthing went wrong in firebase", MainActivity.this);
        }
        pb.setVisibility(View.GONE);

    }





}

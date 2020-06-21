package com.example.ScheduleManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class timeTableList extends AppCompatActivity {


    private ListView listView;
   // EditText t1;
    //String key="is Initial value";
    String inputItem;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reff2 ,reff22;
    ArrayList<String> myTimeData;
    ArrayAdapter<String> arrayAdapter;
    ProgressBar pbb;
    TextView txtv;
    int count=0;
    String whatNeeded;

//    DatabaseReference bruceWayneRef = DatabaseReference.getInstance().getReference()
//            .child("user_base/371298");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_list);
        Bundle bundle=getIntent().getExtras();
        txtv=findViewById(R.id.publisher);
//pbb=findViewById(R.id.myProgressBar2);
        inputItem=bundle.getString("stringToPass");
        whatNeeded= bundle.getString("whatweneed");
        reff2= firebaseDatabase.getReference("staff/"+inputItem+"/Table");
        //t1=findViewById(R.id.inputedText);
        myTimeData=new ArrayList<>();
        listView = findViewById(R.id.timeTableList);
        txtv.setText("Published By: "+inputItem);
         arrayAdapter = new ArrayAdapter<>(timeTableList.this, android.R.layout.simple_list_item_1,myTimeData);
         listView.setAdapter(arrayAdapter);
Bundle bundle11=getIntent().getExtras();
        whatNeeded=bundle11.getString("whatweneed");
if(whatNeeded.equals("timetableee"))
        showDataTimeTable();
if(whatNeeded.equals("messagess"))
        showMessages();
       // showListView();

    }




    private void showListView(){

        try {

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(timeTableList.this, android.R.layout.simple_list_item_1,myTimeData);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String value;
                    value = (String) listView.getItemAtPosition(position);

                   // Toast.makeText(timeTableList.this," is started!!",Toast.LENGTH_SHORT).show();

                }
            });
        }catch(Exception e)
        {

            Toast.makeText(timeTableList.this," somthing went wrong in ArrayList"+e.toString(),Toast.LENGTH_SHORT).show();

        }
    }

public void showMessages(){

    reff22= firebaseDatabase.getReference("messages/"+inputItem);

        reff22.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {

                                message mssg = dataSnapshot2.getValue(message.class);
                                if (mssg != null) {

                                    myTimeData.add("\n\t\tPublished on: "+mssg.getTimeSent()+"\n\t\t==========================\n"+mssg.getMessagBody());
                                    arrayAdapter.notifyDataSetChanged();
                                    // pbb.setVisibility(View.GONE);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError1) {

                        }
                    });





}

//
//    day Day=new day();
//                Day.setAction(action.getText().toString());
//                Day.setPeriod(startT.getText().toString()+"-"+endT.getText().toString());
//
//    DatabaseReference ref = firebaseDatabase.getReference("staff").child(passedUserName).child("Table");
//                try {
//        ref.child(day.getText().toString()).push().setValue(Day);
//        mainActivity.displayMessage("Well done!!",getApplicationContext());
//    }catch (Exception e){
//        mainActivity.displayMessage(e.toString(),getApplicationContext());
//
//    }
//


    public void showDataTimeTable(){
        //DatabaseReference ref = firebaseDatabase.getReference("staff").child("inputItem").child("Table");

reff2.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
           // pbb.setProgress(++count);
          String   key= dataSnapshot1.getKey();

            DatabaseReference reff3= firebaseDatabase.getReference("staff/"+inputItem+"/Table/"+key);

            reff3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                    String key12 = dataSnapshot1.getKey();
                    myTimeData.add("\t\t\t"+key12+"\n\t\t\t========");
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {

                        day Day2 = dataSnapshot2.getValue(day.class);
                        if (Day2 != null) {

                            myTimeData.add("\n\t\t\t"+Day2.getPeriod()+"\n"+Day2.getAction());
      arrayAdapter.notifyDataSetChanged();
     // pbb.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError1) {

                }
            });


        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});


    }


}
